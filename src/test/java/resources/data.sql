INSERT INTO dock (name, city, capacity, occupied)
    VALUES ('Puerto del Este', 'Valencia', 1500, 300),
    ('Puerto del Norte', 'Bilbao', 3000, 200),
    ('Puerto Inexistente', 'Andorra', 500, 20);

INSERT INTO ship (name, captain, crewship_members, type, dock)
    VALUES ('Angelino', 'Juana', 14, 'FISHING', 1),
           ('El Travieso', 'Roberto', 4, 'PERSONAL', 1),
           ('Lobo de Mar', 'Laura', 26, 'CARGO', 1);