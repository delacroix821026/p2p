package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IStudentDao;
import com.newland.financial.p2p.domain.entity.Student;
import org.springframework.stereotype.Repository;
/**
 * @author ceshi
 * */
@Repository
public class StudentDao extends MybatisBaseDao<Student>
        implements IStudentDao {
    /**
     *@param student Student
     * */
    public void insert(final Student student) {
        super.insertSelective(student);
    }
}
