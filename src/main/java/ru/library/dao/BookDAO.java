package ru.library.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.library.models.Book;
import ru.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBook() {
        return jdbcTemplate.query("SELECT * FROM book", new BookMapper());
    }

    public Book findById(Long idBook) {
        return jdbcTemplate.queryForObject("select * from book where idBook = ?",
                new Object[]{idBook} , new BookMapper());
    }
    public void save(Book book) {
        jdbcTemplate.update("insert into book(name, author,year) values(?, ?, ?)"
                , book.getName(),book.getAuthor(),book.getYear());

    }
    public void update(Book book, Integer idBook) {
        jdbcTemplate.update("update book set name = ?, author = ? ,year = ? where idBook = ?",
               book.getName(),book.getAuthor(),book.getYear(),  idBook);
    }

    public void delete(Integer idBook) {
        jdbcTemplate.update("delete from book where idBook = ?", idBook);
    }

    public Optional<Person> getBookOwner(Long idBook) {
        return jdbcTemplate.query("select person. * from book join person on book.p_id = person.idPerson where book.idBook = ?",
                new Object[]{idBook}, new PersonMapper()).stream().findAny();
    }
    public void takeBookReturn(Long idBook) {
        jdbcTemplate.update("update book set p_id = null where idBook=?", idBook);
    }

    public void giveBook(Long idBook, Person person) {
        jdbcTemplate.update("update book set p_id=? where idBook=?",
                person.getIdPerson(), idBook);
    }
}

