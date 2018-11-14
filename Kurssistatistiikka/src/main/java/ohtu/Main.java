package ohtu;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
        String studentNr = "012345678";
        if (args.length > 0) {
            studentNr = args[0];
        }

        String urlSubs = "https://studies.cs.helsinki.fi/courses/students/" + studentNr + "/submissions";
        String urlCourse = "https://studies.cs.helsinki.fi/courses/courseinfo";
        String urlOhtuStat = "https://studies.cs.helsinki.fi/courses/ohtu2018/stats";
        String urlRailsStat = "https://studies.cs.helsinki.fi/courses/rails2018/stats";

        String SubBodyText = Request.Get(urlSubs).execute().returnContent().asString();
        String CourseBodyText = Request.Get(urlCourse).execute().returnContent().asString();
        String OhtuStatBodyText = Request.Get(urlOhtuStat).execute().returnContent().asString();
        String RailsStatBodyText = Request.Get(urlRailsStat).execute().returnContent().asString();

        JsonParser parser = new JsonParser();
        JsonObject ohtuData = parser.parse(OhtuStatBodyText).getAsJsonObject();
        JsonObject railsData = parser.parse(RailsStatBodyText).getAsJsonObject();
        
        HashMap<String, int[]> stats = new HashMap<>();
        
        // Hae kurssistatistiikkaa
        int ohtuStud = ohtuData.entrySet()
                .stream()
                .mapToInt(w -> w.getValue()
                        .getAsJsonObject().get("students").getAsInt()).sum();
        
        int ohtuRet = ohtuData.entrySet()
                .stream()
                .mapToInt(w -> w.getValue()
                        .getAsJsonObject().get("exercise_total").getAsInt()).sum();
        
        int ohtuHours = ohtuData.entrySet()
                .stream()
                .mapToInt(w -> w.getValue()
                        .getAsJsonObject().get("hour_total").getAsInt()).sum();
        
        stats.put("ohtu2018", new int[]{ohtuStud, ohtuRet, ohtuHours});
        
        int railsStud = railsData.entrySet()
                .stream()
                .mapToInt(w -> w.getValue()
                        .getAsJsonObject().get("students").getAsInt()).sum();
        
        int railsRet = railsData.entrySet()
                .stream()
                .mapToInt(w -> w.getValue()
                        .getAsJsonObject().get("exercise_total").getAsInt()).sum();
        
        int railsHours = railsData.entrySet()
                .stream()
                .mapToInt(w -> w.getValue()
                        .getAsJsonObject().get("hour_total").getAsInt()).sum();
        
        stats.put("rails2018", new int[]{railsStud, railsRet, railsHours});
        
        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(SubBodyText, Submission[].class);
        Course[] courses = mapper.fromJson(CourseBodyText, Course[].class);

        Set<String> studentCourses = new HashSet<>();
        for (Submission sub : subs) {
            if (!studentCourses.contains(sub.getCourse())) {
                studentCourses.add(sub.getCourse());
            }
        }

        System.out.println("OHTU STUDENTS: " + ohtuStud);
        
        System.out.println("Student number: " + studentNr);
        System.out.println("");
        for (Course course : courses) {
            if (studentCourses.contains(course.getName())) {
                int exercises = 0;
                int hours = 0;

                System.out.println(course.getFullName() + "\n");
                for (Submission submission : subs) {
                    if (submission.getCourse().equals(course.getName())) {
                        exercises += submission.getExercises().size();
                        hours += submission.getHours();
                        System.out.println(submission);
                    }
                }
                System.out.println("");
                System.out.println("total: " + exercises+ "/" + course.getTotalExercises() + " exercises " + hours + " hours");
                System.out.println("");
                System.out.println(
                        "total submissions: " + stats.get(course.getName())[0] +
                        ", total returned exercises: " + stats.get(course.getName())[1] +
                        ", total time spent: " + stats.get(course.getName())[2]
                );
                System.out.println("");
            }
        }
    }
}
