package run.hxtia.workbd.test.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import run.hxtia.workbd.WorkBoardApplication;
import run.hxtia.workbd.mapper.StudentMapper;
import run.hxtia.workbd.pojo.po.Student;

@SpringBootTest(classes = WorkBoardApplication.class)
@Rollback
@AutoConfigureMockMvc
public class StudentMapperTest {

    @Autowired
    StudentMapper studentMapper;

    @Test
    public void testKey() {

        Student student = new Student();
        student.setNickname("222");
        student.setOpenid("221");

        studentMapper.insert(student);

        System.out.println(student.getId());
        System.out.println(student.getNickname());

    }
}
