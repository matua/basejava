package com.matuageorge.webapp;

import com.matuageorge.webapp.model.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    static final Resume masterResume;

    static {
        masterResume = new Resume("uuid_1", "Gregory Kislin");

        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

        masterResume.setContacts(contacts);
        masterResume.setSections(sections);

        contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.EMAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        contacts.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        contacts.put(ContactType.HOMEPAGE, "http://gkislin.ru/");

        sections.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java" +
                " Web и Enterprise технологиям"));
        sections.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры."));

        //Достижения
        List<String> achievementsListSectionBulletsList = new ArrayList<>();

        achievementsListSectionBulletsList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное" +
                " взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 " +
                "выпускников. ");
        achievementsListSectionBulletsList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами " +
                "Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk. ");
        achievementsListSectionBulletsList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера. ");
        achievementsListSectionBulletsList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, " +
                "Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга. ");
        achievementsListSectionBulletsList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных" +
                " сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации" +
                " о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и " +
                "мониторинга системы по JMX (Jython/ Django). ");
        achievementsListSectionBulletsList.add("Реализация протоколов по приему платежей всех основных платежных системы России" +
                " (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        sections.put(SectionType.ACHIEVEMENT, new ListSection(achievementsListSectionBulletsList));


        //Квалификация
        List<String> qualificationsListSectionBulletsList = new ArrayList<>();

        qualificationsListSectionBulletsList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ");
        qualificationsListSectionBulletsList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce ");
        qualificationsListSectionBulletsList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        qualificationsListSectionBulletsList.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualificationsListSectionBulletsList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy, ");
        qualificationsListSectionBulletsList.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualificationsListSectionBulletsList.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis," +
                " Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, " +
                "ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements). ");
        qualificationsListSectionBulletsList.add("Python: Django.");
        qualificationsListSectionBulletsList.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js ");
        qualificationsListSectionBulletsList.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka ");
        qualificationsListSectionBulletsList.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, " +
                "StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, " +
                "BPMN2, LDAP, OAuth1, OAuth2, JWT. ");
        qualificationsListSectionBulletsList.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualificationsListSectionBulletsList.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, " +
                "Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualificationsListSectionBulletsList.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования," +
                " архитектурных шаблонов, UML, функционального программирования ");
        qualificationsListSectionBulletsList.add("Родной русский, английский \"upper intermediate\"");

        sections.put(SectionType.QUALIFICATIONS, new ListSection(qualificationsListSectionBulletsList));

        //Опыт работы
        List<Organization> experienceOrganizationSectionList = new ArrayList<>();
        experienceOrganizationSectionList.add(new Organization("Java Online Projects", "http://javaops.ru/",
                new Organization.Position(2013, Month.OCTOBER, "Автор проекта.", "Создание," +
                        " организация и проведение Java онлайн проектов и стажировок.")));
        experienceOrganizationSectionList.add(new Organization("Wrike", "https://www.wrike.com/",
                new Organization.Position(2014, Month.OCTOBER, 2016, Month.JANUARY, "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis," +
                                " Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")));
        experienceOrganizationSectionList.add(new Organization("RIT Center", "",
                new Organization.Position(2012, Month.APRIL, 2014, Month.OCTOBER, "Java архитектор",
                        "Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                                "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование " +
                                "системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка " +
                                "интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, " +
                                "экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера " +
                                "документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security," +
                                " Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting" +
                                " via ssh tunnels, PL/Python")));
        experienceOrganizationSectionList.add(new Organization("Wrike", "https://www.wrike.com/",
                new Organization.Position(2014, Month.OCTOBER, 2016, Month.JANUARY, "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis," +
                                " Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")));

        sections.put(SectionType.EXPERIENCE, new OrganizationSection(experienceOrganizationSectionList));

        List<Organization> educationOrganizationSectionList = new ArrayList<>();

        educationOrganizationSectionList.add(new Organization("Coursera", "https://www.coursera.org/course/progfun",
                new Organization.Position(2013, Month.MARCH, 2013, Month.MAY, "Functional Programming Principles in Scala\" by Martin Odersky", null)));
        educationOrganizationSectionList.add(new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                new Organization.Position(2011, Month.MARCH, 2011, Month.APRIL, "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null)));
        educationOrganizationSectionList.add(new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "http://www.ifmo.ru/",
                new Organization.Position(1993, Month.SEPTEMBER, 1996, Month.JULY, "Аспирантура (программист С, С++)", null),
                new Organization.Position(1987, Month.SEPTEMBER, 1993, Month.JULY, "Инженер (программист Fortran, C)", null)));

        sections.put(SectionType.EDUCATION, new OrganizationSection(educationOrganizationSectionList));
    }

    public static void main(String[] args) {
        System.out.println(masterResume);
    }

    public static Resume returnTestResume() {
        return masterResume;
    }
}