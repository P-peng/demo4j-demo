package com.ge.test;

import com.ge.entity.ClassInfo;
import com.ge.entity.User;
import org.junit.Test;

/**
 * @author dengzhipeng
 * @date 2019/06/14
 */
public class ClassInfoTest {
    @Test
    public void t1() throws Exception {
        ClassInfo info = new ClassInfo();
        System.out.println(info.hashCode());
    }
}
