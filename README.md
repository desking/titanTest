# titanTest
Конкеш:

```
<Resource name="jdbc/titanTest" factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
              auth="Container"
              type="javax.sql.DataSource"
              username="titan_user"
              password="123456"
              url="jdbc:postgresql://localhost:5432/titandb"
              driverClassName="org.postgresql.Driver"
              initialSize="20"
              maxWaitMillis="15000"
              maxTotal="75"
              maxIdle="20"
              maxAge="7200000"
              testOnBorrow="true"
              validationQuery="select 1"
              />
```
              
Дамп базы: backup.tar              