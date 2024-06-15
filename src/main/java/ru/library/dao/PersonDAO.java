package ru.library.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.library.models.Book;
import ru.library.models.Person;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAllPeople() {
        return jdbcTemplate.query("SELECT * FROM person", new PersonMapper());
    }


    public Person findById(Long idPerson) {
        return jdbcTemplate.queryForObject("select * from person where idPerson = ?",
                new Object[]{idPerson} , new PersonMapper());
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into person(fullName, yearOfBirth) values( ?, ?)"
                , person.getFullName(), person.getYearOfBirth());

    }

    public void update(Person person, Integer idPerson) {
        jdbcTemplate.update("update person set fullName = ?, yearOfBirth = ? where idPerson = ?",
                person.getFullName(), person.getYearOfBirth(),  idPerson);
    }

    public void delete(Integer idPerson) {
        jdbcTemplate.update("delete from person where idPerson = ?", idPerson);
    }

    public List<Book> bookByPerson(Long idPerson){
        return jdbcTemplate.query("select book. * from person join book on person.idPerson = book.p_id where idPerson=?",new Object[]{idPerson},new BookMapper());
    }
}