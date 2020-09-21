package com.matuageorge.webapp;

import com.matuageorge.webapp.model.*;

import java.time.Month;
import java.util.*;

public class ResumeTestData2 {
    static final Resume masterResume;

    static {
        masterResume = new Resume(String.valueOf(UUID.randomUUID()), "Ivan Popov");

        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

        masterResume.setContacts(contacts);
        masterResume.setSections(sections);

        contacts.put(ContactType.PHONE, "+7(234) 342-34576");
        contacts.put(ContactType.SKYPE, "ivan.popov");
        contacts.put(ContactType.EMAIL, "ivan@popov.ru");
        contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/ipopov");
        contacts.put(ContactType.GITHUB, "https://github.com/ipopov");
        contacts.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/123456");
        contacts.put(ContactType.HOMEPAGE, "http://ipopov.ru/");

        sections.put(SectionType.OBJECTIVE, new TextSection("Leading internships and corporate training in Java Web and Enterprise technologies."));
        sections.put(SectionType.PERSONAL, new TextSection("Analytical mindset, strong logic, creativity, initiative."));

        //Достижения
        List<String> achievementsListSectionBulletsList = new ArrayList<>();

        achievementsListSectionBulletsList.add("Since 2013: development of projects \"Web Application Development\", \"Java Enterprise\",\n" +
                "\"Multimodal maven. Multithreading. XML (JAXB / StAX). Web services (JAX-RS / SOAP). Remote\n" +
                "  interaction (JMS / AKKA) \". Organization of online internships and project management. More than 1000\n" +
                "graduates.");
        achievementsListSectionBulletsList.add("Implementing two-factor authentication for an online project management platform\n" +
                "Wrike. Integration with Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementsListSectionBulletsList.add("Establishment of the development process and continuous integration of ERP system River BPM.\n" +
                "Integration with 1C, Bonita BPM, CMIS, LDAP. Developing an environment management application on the stack:\n" +
                "Scala / Play / Anorm / JQuery. Development of SSO authentication and authorization of various ERP modules,\n" +
                "CIFS / SMB java server integration.");
        achievementsListSectionBulletsList.add("Implementation of Rich Internet Application from scratch on the JPA technology stack,\n" +
                "Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock for algorithmic trading.");
        achievementsListSectionBulletsList.add("Building a JavaEE framework for fault-tolerant loosely coupled communication\n" +
                "  services (SOA-base architecture, JAX-WS, JMS, AS Glassfish). Collection of statistics of services and information\n" +
                "  status through the Nagios monitoring system. Implementation of an online client for administration and\n" +
                "system monitoring by JMX (Jython / Django).");
        achievementsListSectionBulletsList.add("Implementation of protocols for accepting payments of all major payment systems in Russia\n" +
                "  (Cyberplat, Eport, Chronopay, Sberbank), Belarus (Erip, Osmp) and Nicaragua.");

        sections.put(SectionType.ACHIEVEMENT, new ListSection(achievementsListSectionBulletsList));


        //Квалификация
        List<String> qualificationsListSectionBulletsList = new ArrayList<>();

        qualificationsListSectionBulletsList.add("Tools: Maven + plugin development, Gradle, Nginx setup");
        qualificationsListSectionBulletsList.add("Hudson / Jenkins administration, Ant + custom task, SoapUI, JPublisher,\n" +
                "Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualificationsListSectionBulletsList.add("Excellent knowledge and experience in applying the concepts of OOP, SOA, design patterns,\n" +
                "  architectural patterns, UML, functional programming");
        qualificationsListSectionBulletsList.add("Native Russian, upper intermediate English");

        sections.put(SectionType.QUALIFICATIONS, new ListSection(qualificationsListSectionBulletsList));

        //Опыт работы
        List<Organization> experienceOrganizationSectionList = new ArrayList<>();
        experienceOrganizationSectionList.add(new Organization("Popov Projects Inc.", "http://ipopov.ru/",
                new Organization.Position(2003, Month.OCTOBER, "Title", "Creature,\n" +
                        "  organization and conduct of Java online projects and internships.")));
        experienceOrganizationSectionList.add(new Organization("Wrike", "https://www.wrike.com/",
                new Organization.Position(2004, Month.OCTOBER, 2006, Month.JANUARY, "Senior Developer (backend)",
                        "Design and development of an online project management platform Wrike (Java 8 API, Maven, Spring, MyBatis,\n" +
                                "  Guava, Vaadin, PostgreSQL, Redis). Two-factor authentication, authorization via OAuth1, OAuth2, JWT SSO.")));
        experienceOrganizationSectionList.add(new Organization("POP Center", "",
                new Organization.Position(2002, Month.APRIL, 2004, Month.OCTOBER, "Java architect",
                        "Organization of the ERP system development process for different environments: release policy,\n" +
                                "versioning, CI maintenance (Jenkins), database migration (Flyway customization), configuration\n" +
                                "systems (pgBoucer, Nginx), AAA via SSO. The architecture of the database and the server side of the system. Development\n" +
                                "integration services: CMIS, BPMN2, 1C (WebServices), general-purpose services (mail,\n" +
                                "export to pdf, doc, html). Alfresco JLAN integration for online editing from the browser\n" +
                                "MS Office documents. Maven + plugin development, Ant, Apache Commons, Spring security,\n" +
                                "  Spring MVC, Tomcat, WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting\n" +
                                "  via ssh tunnels, PL / Python")));

//        sections.put(SectionType.EXPERIENCE, new OrganizationSection(experienceOrganizationSectionList));

        List<Organization> educationOrganizationSectionList = new ArrayList<>();

        educationOrganizationSectionList.add(new Organization("Coursera", "https://www.coursera.org/course/progfun",
                new Organization.Position(2013, Month.MARCH, 2013, Month.MAY, "Functional Programming Principles in Scala\" by Martin Odersky", null)));
        educationOrganizationSectionList.add(new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                new Organization.Position(2011, Month.MARCH, 2011, Month.APRIL, "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null)));
        educationOrganizationSectionList.add(new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "http://www.ifmo.ru/",
                new Organization.Position(1993, Month.SEPTEMBER, 1996, Month.JULY, "Аспирантура (программист С, С++)", null),
                new Organization.Position(1987, Month.SEPTEMBER, 1993, Month.JULY, "Инженер (программист Fortran, C)", null)));
        educationOrganizationSectionList.add(new Organization("No Link Organization",
                null,
                new Organization.Position(1985, Month.JANUARY, 1985, Month.AUGUST, "Position without description", null)));

//        sections.put(SectionType.EDUCATION, new OrganizationSection(educationOrganizationSectionList));
    }

    public static void main(String[] args) {
        System.out.println(masterResume);
    }

    public static Resume returnTestResume() {
        return masterResume;
    }
}