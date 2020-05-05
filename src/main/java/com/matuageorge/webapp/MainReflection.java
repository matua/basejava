package com.matuageorge.webapp;

import com.matuageorge.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("Matua");
        Field field = resume.getClass().getDeclaredFields()[0];
        field.setAccessible(true);

        System.out.println(field.getName());
        System.out.println(field.get(resume));

        field.set(resume, "new uuid");

        System.out.println(resume);

        field.set(resume, UUID.randomUUID().toString());

        Method toStringMethod = Resume.class.getDeclaredMethod("toString");

        System.out.println("Executing toString() method via reflection. Result: " + toStringMethod.invoke(resume));

    }
}
