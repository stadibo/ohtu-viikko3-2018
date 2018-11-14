package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
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

        String SubBodyText = Request.Get(urlSubs).execute().returnContent().asString();
        String CourseBodyText = Request.Get(urlCourse).execute().returnContent().asString();

//        System.out.println("json-muotoinen data:");
//        System.out.println(CourseBodyText);

        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(SubBodyText, Submission[].class);
        Course[] courses = mapper.fromJson(CourseBodyText, Course[].class);

        Set<String> studentCourses = new HashSet<>();
        for (Submission sub : subs) {
            if (!studentCourses.contains(sub.getCourse())) {
                studentCourses.add(sub.getCourse());
            }
        }

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
            }
        }
    }
}
