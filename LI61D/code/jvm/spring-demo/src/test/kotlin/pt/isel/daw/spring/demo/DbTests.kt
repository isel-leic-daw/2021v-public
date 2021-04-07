package pt.isel.daw.spring.demo

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.withHandleUnchecked
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

data class Student(
    val id: Int,
    val number: Int,
    val name: String
)

@SpringBootTest
class DbTests {

    @Autowired
    lateinit var jdbi: Jdbi

    @Test
    fun can_access_db() {
        val students = jdbi.withHandleUnchecked { handle ->
            handle.createQuery("select * from dbo.students")
                .mapTo(Student::class.java)
                .list()
        }
        Assertions.assertEquals(2, students.size)
    }
}