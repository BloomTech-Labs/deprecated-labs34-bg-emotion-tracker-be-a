package com.lambdaschool.oktafoundation;

import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CommandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@ConditionalOnProperty(
    prefix = "command.line.runner",
    value = "enabled",
    havingValue = "true",
    matchIfMissing = true)
@Component
public class SeedData
    implements CommandLineRunner
{
    /**
     * Connects the Role Service to this process
     */
    @Autowired
    RoleService roleService;
    /**
     * Connects the user service to this process
     */
    @Autowired
    UserService userService;

    @Autowired
    ProgramService programService;

    @Autowired
    ClubService clubService;

    @Autowired
    ReactionsService reactionsService;

    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data using Java Faker is seeded into our database.
     * Note this process does not remove data from the database. So if data exists in the database
     * prior to running this process, that data remains in the database.
     *
     * @param args The parameter is required by the parent interface but is not used in this process.
     */
    @Transactional
    @Override
    public void run(String[] args) throws
                                   Exception
    {
        userService.deleteAll();
        roleService.deleteAll();
        clubService.deleteAll();
        programService.deleteAll();
        reactionsService.deleteAll();

        Role r1 = new Role("superadmin");
        Role r2 = new Role("clubdir");
        Role r3 = new Role("ydp");
        Role r4 = new Role("user");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);
        r4 = roleService.save(r4);

        Reactions re1 = new Reactions("Happy","\uD83D\uDE00");
        Reactions re2 = new Reactions("Sad","\u2639\uFE0F");
        Reactions re3 = new Reactions("Angry", "\uE059");
        Reactions re4 = new Reactions("Tired", "\uD83D\uDE2A");
        Reactions re5 = new Reactions("Sick","\uD83E\uDD12");
        Reactions re6 = new Reactions("Didn't Like","\uD83D\uDC4E");
        Reactions re7 = new Reactions("Mind Blown","\uD83E\uDD2F");
        Reactions re8 = new Reactions("Like","\uD83D\uDC4D" );

        re1 = reactionsService.save(re1);
        re2 = reactionsService.save(re2);
        re3 = reactionsService.save(re3);
        re4 = reactionsService.save(re4);
        re5 = reactionsService.save(re5);
        re6 = reactionsService.save(re6);
        re7 = reactionsService.save(re7);
        re8 = reactionsService.save(re8);

        // Super Admin
        User u1 = new User("llama001@maildrop.cc");
        u1.getRoles()
            .add(new UserRoles(u1,
                r1));
        userService.save(u1);


        // Club Directors
        User u2 = new User("llama002@maildrop.cc");
        u2.getRoles()
            .add(new UserRoles(u2,
                r2));
        userService.save(u2);

        User u3 = new User("llama003@maildrop.cc");
        u3.getRoles()
            .add(new UserRoles(u3,
                r2));
        userService.save(u3);

        User u4 = new User("llama004@maildrop.cc");
        u4.getRoles()
            .add(new UserRoles(u4,
                r2));
        userService.save(u4);

        // Youth Development Professionals
        User u5 = new User("llama005@maildrop.cc");
        u5.getRoles()
            .add(new UserRoles(u5,
                r3));
        userService.save(u5);

        User u6 = new User("llama006@maildrop.cc");
        u6.getRoles()
            .add(new UserRoles(u6,
                r3));
        userService.save(u6);

        User u7 = new User("llama007@maildrop.cc");
        u7.getRoles()
            .add(new UserRoles(u7,
                r4));
        userService.save(u7);

        Program p1 = new Program("Volleyball");
        Program p2 = new Program("Dance");
        Program p3 = new Program("Football");
        Program p4 = new Program("Basketball");
        Program p5 = new Program("Baseball");


        p1 = programService.save(p1);
        p2 = programService.save(p2);
        p3 = programService.save(p3);
        p4 = programService.save(p4);
        p5 = programService.save(p5);

        Club c1 = new Club( "club1", "llama002@maildrop.cc");
        c1.getPrograms()
            .add(new ClubPrograms(c1,p1));
        c1.getPrograms()
            .add(new ClubPrograms(c1,p2));
        c1.getPrograms()
            .add(new ClubPrograms(c1,p3));
        c1.getPrograms()
            .add(new ClubPrograms(c1,p4));
        c1.getPrograms()
            .add(new ClubPrograms(c1,p5));
        clubService.save(c1);

        Club c2 = new Club( "club2", "llama003@maildrop.cc");
        c2.getPrograms()
            .add(new ClubPrograms(c2,p1));
        c2.getPrograms()
            .add(new ClubPrograms(c2,p2));
        c2.getPrograms()
            .add(new ClubPrograms(c2,p4));
        clubService.save(c2);

        Club c3 = new Club( "club3",  "llama004@maildrop.cc");
        c3.getPrograms()
            .add(new ClubPrograms(c3,p1));
        c3.getPrograms()
            .add(new ClubPrograms(c3,p2));
        c3.getPrograms()
            .add(new ClubPrograms(c3,p3));
        c3.getPrograms()
            .add(new ClubPrograms(c3,p4));
        clubService.save(c3);

        // hard coding club data for addition of programs by csv file
        // associating programs with clubname for many to many relationship
        // convert to form, CSV, or integration with stakeholder management system in future release

        Club c4 = new Club( "anderson", "andrew lorenzo");
        clubService.save(c4);
        Club c5 = new Club( "grossman", "henry segovia");
        clubService.save(c5);
        Club c6 = new Club( "jefferson", "jennifer wissusik");
        clubService.save(c6);
        Club c7 = new Club( "johnston", "jennifer wissusik");
        clubService.save(c7);
        Club c8 = new Club( "morton", "lisa barron");
        clubService.save(c8);
        Club c9 = new Club( "notter", "leslie chicas");
        clubService.save(c9);
        Club c10 = new Club( "catlin", "");
        clubService.save(c10);
        Club c11 = new Club( "marley", "");
        clubService.save(c11);
        Club c12 = new Club( "stelle", "");
        clubService.save(c12);
    }
}