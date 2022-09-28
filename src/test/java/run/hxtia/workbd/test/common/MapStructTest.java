package run.hxtia.workbd.test.common;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import run.hxtia.workbd.common.upload.UploadReqParam;
import run.hxtia.workbd.pojo.po.User;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootTest
public class MapStructTest {

    @Test
    public void mapStruct() {
        // UserVo userVo = MapStructs.INSTANCE.po2vo();
        // System.out.println(userVo);
    }

}
