package com.matuageorge.webapp;

import com.matuageorge.webapp.model.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.YearMonth;
import java.util.*;

public class ResumeTestData {
    private static final Resume masterResume;

    static {
        masterResume = new Resume("new_uuid", "Григорий Кислин");

        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

        contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.EMAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        contacts.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        contacts.put(ContactType.HOMEPAGE, "http://gkislin.ru/");

        Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

        sections.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java" +
                " Web и Enterprise технологиям"));
        sections.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры. "));

        //Достижения
        List<String> achievementsBullets = new ArrayList<>();

        achievementsBullets.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное" +
                " взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 " +
                "выпускников. ");
        achievementsBullets.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами " +
                "Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk. ");
        achievementsBullets.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера. ");
        achievementsBullets.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, " +
                "Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга. ");
        achievementsBullets.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных" +
                " сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации" +
                " о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и " +
                "мониторинга системы по JMX (Jython/ Django). ");
        achievementsBullets.add("Реализация протоколов по приему платежей всех основных платежных системы России" +
                " (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        sections.put(SectionType.ACHIEVEMENT, new BulletsSection(achievementsBullets));


        //Квалификация
        List<String> qualificaationBullets = new ArrayList<>();

        qualificaationBullets.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ");
        qualificaationBullets.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce ");
        qualificaationBullets.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        qualificaationBullets.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualificaationBullets.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy, ");
        qualificaationBullets.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualificaationBullets.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis," +
                " Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, " +
                "ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements). ");
        qualificaationBullets.add("Python: Django.");
        qualificaationBullets.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js ");
        qualificaationBullets.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka ");
        qualificaationBullets.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, " +
                "StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, " +
                "BPMN2, LDAP, OAuth1, OAuth2, JWT. ");
        qualificaationBullets.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualificaationBullets.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, " +
                "Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualificaationBullets.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования," +
                " архитектурных шаблонов, UML, функционального программирования ");
        qualificaationBullets.add("Родной русский, английский \"upper intermediate\"");

        sections.put(SectionType.QUALIFICATIONS, new BulletsSection(qualificaationBullets));

        //Опыт работы

        List<Organization> experienceList = new ArrayList<>();
        AbstractSection experience = new OrganizationSection(experienceList);
        Map<YearMonth, YearMonth> period1 = new HashMap<>();
        Map<YearMonth, YearMonth> period2 = new HashMap<>();

        period1.put(YearMonth.of(2013, 10), YearMonth.now());
        period2.put(YearMonth.of(2014, 10), YearMonth.of(2016, 1));

        try {
            experienceList.add(new Organization
                    (new WebLink("Java Online Projects", new URL("http://javaops.ru/")), period1,
                            new TextSection("Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                    "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная " +
                                    "аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")));

            experienceList.add(new Organization
                    (new WebLink("RIT Center"),
                            period2,
                            new TextSection("Организация процесса разработки системы ERP для разных " +
                                    "окружений: релизная политика, версионирование, ведение CI (Jenkins), " +
                                    "миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, " +
                                    "Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка " +
                                    "интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего " +
                                    "назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN " +
                                    "для online редактирование из браузера документов MS Office. Maven + plugin " +
                                    "development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, " +
                                    "xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via" +
                                    " ssh tunnels, PL/Python")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        sections.put(SectionType.EXPERIENCE, new OrganizationSection(experienceList));

        //Образование

        List<Organization> educationList = new ArrayList<>();
        AbstractSection education = new OrganizationSection(educationList);

        Map<YearMonth, YearMonth> educationPeriod1 = new HashMap<>();
        Map<YearMonth, YearMonth> educationPeriod2 = new HashMap<>();

        educationPeriod1.put(YearMonth.of(2011, 3), YearMonth.of(2011, 4));
        educationPeriod2.put(YearMonth.of(2000, 1), YearMonth.of(2002, 4));
        educationPeriod2.put(YearMonth.of(2005, 1), YearMonth.of(2005, 4));


        try {
            educationList.add(new Organization
                    (new WebLink("Coursera", new URL("https://www.coursera.org/course/progfun")),
                            educationPeriod1,
                            new TextSection("\"Functional Programming Principles in Scala\" by Martin Odersky")));

            educationList.add(new Organization
                    (new WebLink("Luxoft", new URL("http://www.luxoft-training.ru/training/catalog/course.html?ID=22366")),
                            educationPeriod1,
                            new TextSection("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"")));

            educationList.add(new Organization
                    (new WebLink("Siemens AG", new URL("http://www.siemens.ru/")),
                            educationPeriod2,
                            new TextSection("3 месяца обучения мобильным IN сетям (Берлин)")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        sections.put(SectionType.EDUCATION, new OrganizationSection(educationList));


        masterResume.setContacts(contacts);
        masterResume.setSections(sections);

    }

    public static void main(String[] args) {


        System.out.println(masterResume);

    }

    public static Resume returnTestResume() {
        return masterResume;
    }
}
