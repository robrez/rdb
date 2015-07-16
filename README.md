# rdb
Java Object-Oriented SQL and CRUD.

I started this project a _very_ long time ago and just haven't found time to give it any TLC since my initial onslaught of coding. Maybe if I put it on github I'll think about it more often.

While I do like my mental roadmap for this project, I can highly recommend jooq or queryDSL as similar alternatives that are much, much, much more mature.

That said....

# generate
Entities
DAO for CRUD
Meta classes for queries.

```
java -cp rdb.jar com.rdb.generate.Generator tables=user_id,userid_ext package=com.rdb.util output-directory=C:\dbgen host=jdbc:postgresql://url.com:5432/dbname user=user pw=password
```

# sql
Java Code:
```
    //Note: Meta classes are prefixed w/ "R", corresponding entity classes are not.
    RUserId a = RUserId.as("a");
    RUseridExt b = RUseridExt.as("b");
    Select select = new Select();
    select.from(a)
            .columns(a.allColumns)
            .columns(b.userextActive.as("isactive"), b.userextDt)
            .join(new LeftOuterJoin(b).on(a.useridUid.equalTo(b.userextUserUid)))
            .where(
                    Or.or(
                            a.useridName.like("rob%", "lindsay%"),
                            a.useridUid.equalTo(1, 2)),

                    a.useridEmail.notEqualTo("rob@gmail.com")
            );
    String result = select.toString();
    System.out.println(result);
```

Result:
```
    SELECT
       a.userid_email, a.userid_name, a.userid_uid, b.userext_active AS isactive, b.userext_dt
     FROM user_id AS a
     LEFT OUTER JOIN userid_ext AS b
       ON a.userid_uid = b.userext_user_uid
     WHERE  (  (  ( a.userid_name like 'rob%' OR a.userid_name like 'lindsay%' ) 
       OR  ( a.userid_uid = '1' OR a.userid_uid = '2' )  ) 
       AND a.userid_email != 'rob@gmail.com' ) 
```

Sample Usage:
```
    Db db = Db.connect("host", "user", "password");
    ResultSetWrapper rs = db.executeQuery(select);
    while (rs.next()) {
        Boolean active = rs.getBoolean("isactive", false);
        Integer uid = rs.getInt(a.useridUid.name());
        String email = rs.getString(a.useridName.name());
    }
    rs.close();
```

Loading entities instead... I'm still on the fence about this one. Since a given query may only select _some_ of the fields from a given table, you could end up w/ partially loaded entities.
```
    Db db = Db.connect("host", "user", "password");
    UserIdDao dao = new UserIdDao(db);
    List<UserId> records = dao.loadAll(select);

    //or since we joined two tables...
    ResultSetWrapper rs = db.executeQuery(select);
    UserIdDao userDao = new UserIdDao(db);
    UserIdextDao extDao = new UserIdextDao(db);
    while (rs.next()) {
        UserId userid = userDao.load(rs);
        UserIdext ext = extDao.load(rs);
    }
    rs.close();
```


I'm _really_ not a fan of the approach that popular JPA implementations take in regard to SQL.
```
String somequery = "select userid_email, userid_name, userid_uid where userid_uid = :userid_uid"
```
Where do I begin? Being a string, this is prone to runtime exceptions. For example, if "email" is renamed to "email". I'd prefer compile-time errors. Inflating this and parameterizing it is also painful. Don't even get me started on joins.


# crud
DAOs and entities can handle crud for you. While I don't like SQL in JPA implementors, I will say that they are all quite good at simple CRUD.

# todo
- RDB entity classes are not JPA annotated
- RDB implementation is Postgres-specific (it's the best, anyway)
- Implement a generic, type-safe Record<T1, T2, T3, ..., Tn> class
- Project is a standard Netbeans project. Convert to Maven or Grundle.

