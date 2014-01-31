DELETE FROM ASSETS;
DELETE FROM COMMENTS;
DELETE FROM COMPONENTASSETS;
DELETE FROM COMPONENTS;
DELETE FROM PROJECTCOMPONENTS;
DELETE FROM PROJECTS;
DELETE FROM PROJECTTASKS;
DELETE FROM PROJECTTEAM;
DELETE FROM REPORTCOMMENTS;
DELETE FROM REPORTS;
DELETE FROM TASKASSETS;
DELETE FROM TASKASSIGNEDTO;
DELETE FROM TASKS;
DELETE FROM USERS;

INSERT INTO USERS VALUES ('kirsty','Kirsty','Jones','','password','ProjectManager'),
                            ('ryan','Ryan','Kendall','','password','ProjectCoordinator'),
                            ('craig','Craig','Lewin','','password','QCTeamLeader'),
                            ('user','Test','User','','password','QCTeamMember'),
                            ('client','','','TestClient','password','Client');