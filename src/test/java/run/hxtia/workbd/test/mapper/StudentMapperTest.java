package run.hxtia.workbd.test.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import run.hxtia.workbd.WorkBoardApplication;
<<<<<<<< HEAD:src/test/java/run/hxtia/workbd/test/mapper/OrgMapperTest.java
========
import run.hxtia.workbd.mapper.StudentMapper;
import run.hxtia.workbd.pojo.po.Student;
>>>>>>>> 08dc01a9487e87125b68eb1788b092e7e7b26914:src/test/java/run/hxtia/workbd/test/mapper/StudentMapperTest.java

@SpringBootTest(classes = WorkBoardApplication.class)
@Rollback
@AutoConfigureMockMvc
public class StudentMapperTest {

    @Autowired
<<<<<<<< HEAD:src/test/java/run/hxtia/workbd/test/mapper/OrgMapperTest.java
    CollegeMapper collegeMapper;
========
    StudentMapper studentMapper;
>>>>>>>> 08dc01a9487e87125b68eb1788b092e7e7b26914:src/test/java/run/hxtia/workbd/test/mapper/StudentMapperTest.java

    @Test
    public void testKey() {

<<<<<<<< HEAD:src/test/java/run/hxtia/workbd/test/mapper/OrgMapperTest.java
        Organization organization = new Organization();
        int i = collegeMapper.insertDefaultRegisterOrg(organization);
        System.out.println(organization.getId());
========
        Student student = new Student();
        student.setNickname("222");

        studentMapper.insert(student);

        System.out.println(student.getId());
        System.out.println(student.getNickname());
>>>>>>>> 08dc01a9487e87125b68eb1788b092e7e7b26914:src/test/java/run/hxtia/workbd/test/mapper/StudentMapperTest.java

    }
}
