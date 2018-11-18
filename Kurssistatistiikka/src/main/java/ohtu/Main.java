package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import org.apache.http.client.fluent.Request;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {

    public static void main(String[] args) throws IOException {
        String studentNr = "012345678";
        if (args.length > 0) {
            studentNr = args[0];
        }

        Submission[] subs = getSubmissionsForStudent(studentNr);
        Course[] courses = getCourses();

        System.out.println("opiskelijanumero " + studentNr + "\n");
        System.out.println("");

        for (Course course : courses) {
            System.out.println(course.getFullName() + " " + course.getTerm() + " " + course.getYear());
            System.out.println("");

            int totalDoneExercises = 0;
            int totalHours = 0;
            int totalSubmissions = 0;

            for (Submission sub : subs) {
                if (sub.getCourse().equals(course.getName())) {
                    totalDoneExercises += sub.getExercises().size();
                    totalHours += sub.getHours();

                    System.out.println("viikko " + sub.getWeek() + ":");
                    System.out.println("  tehtyjä tehtäviä " + sub.getExercises().size() + "/" + course.getExercises().get(sub.getWeek()) + " aikaa kului " + sub.getHours() + " tehdyt tehtävät " + sub.getExercises());
                    System.out.println("");

                }
            }

            System.out.println("yhteensä: " + totalDoneExercises + "/" + sumOfExercises(course) + " tunteja " + totalHours);
            System.out.println("");
            
            totalDoneExercises = 0;
            totalHours = 0;

            String courseUrl = "https://studies.cs.helsinki.fi/courses/" + course.getName() + "/stats";
            String bodyText = Request.Get(courseUrl).execute().returnContent().asString();

            JsonParser parser = new JsonParser();
            JsonObject parsittuData = parser.parse(bodyText).getAsJsonObject();

            for (String key : parsittuData.keySet()) {
                totalSubmissions += parsittuData.get(key).getAsJsonObject().get("students").getAsInt();
                totalDoneExercises += parsittuData.get(key).getAsJsonObject().get("exercise_total").getAsInt();
                totalHours += parsittuData.get(key).getAsJsonObject().get("hour_total").getAsInt();
            }

            System.out.println("kurssilla yhteensä " + totalSubmissions
                    + " palautusta, palautettuja tehtäviä " + totalDoneExercises
                    + " kpl, aikaa käytetty yhteensä " + totalHours
                    + " tuntia.");
        }
    }

    public static Submission[] getSubmissionsForStudent(String studentNr) throws IOException {
        String url = "https://studies.cs.helsinki.fi/courses/students/" + studentNr + "/submissions";
        String bodyText = Request.Get(url).execute().returnContent().asString();

        Gson mapper = new Gson();
        return mapper.fromJson(bodyText, Submission[].class);
    }

    public static Course[] getCourses() throws IOException {
        String url = "https://studies.cs.helsinki.fi/courses/courseinfo";
        String bodyText = Request.Get(url).execute().returnContent().asString();

        Gson mapper = new Gson();
        return mapper.fromJson(bodyText, Course[].class);
    }

    public static int sumOfExercises(Course course) {
        int sum = 0;
        for (int exercise : course.getExercises()) {
            sum += exercise;
        }
        return sum;
    }

}
