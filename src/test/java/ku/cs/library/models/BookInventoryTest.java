package ku.cs.library.models;

import ku.cs.services.BookFromYearConditionFilterer;
import ku.cs.services.BookFromYearFilterer;
import ku.cs.services.BookInventoryFileDataSource;
import ku.cs.services.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class BookInventoryTest {
    @Test
    @DisplayName("ถ้าปีมากที่สุดเท่ากัน ให้ตอบเล่มแรกสุดที่เจอ")
    void testMaxBookByPublicationYear(){
        DataSource<BookInventory> dataSource;
        dataSource = new BookInventoryFileDataSource();
        BookInventory bookInventory = dataSource.readData();

        Comparator<Book> yearComparator = new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                int year1 = o1.getPublicationYear();
                int year2 = o2.getPublicationYear();
                if (year1 > year2) return 1;
                if (year1 < year2) return -1;
                return 0;
            }
        };
        Book book = bookInventory.max(yearComparator);
        assertEquals("คู่มือเขียนโปรแกรมด้วยภาษา Java ฉบับสมบูรณ์", book.getName());
    }

    @Test
    @DisplayName("ถ้าปีมากที่สุดเท่ากัน ให้ตอบเล่มแรกสุดที่เจอ")
    void testMaxBookByPublicationYear2(){
        DataSource<BookInventory> dataSource;
        dataSource = new BookInventoryFileDataSource();
        BookInventory bookInventory = dataSource.readData();

        Comparator<Book> yearComparator = new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                if (o1.getPublicationYear() > o2.getPublicationYear()) return 1;
                if (o1.getPublicationYear() < o2.getPublicationYear()) return -1;
                if (o1.getPrice() > o2.getPrice()) return 1;
                if (o1.getPrice() < o2.getPrice()) return -1;
                return 0;
            }
        };
        Book book = bookInventory.max(yearComparator);
        assertEquals("สร้างการเรียนรู้สำหรับ AI ด้วย Python Machine Learning", book.getName());
    }

    @Test
    void testMaxBookByBookName(){
        DataSource<BookInventory> dataSource;
        dataSource = new BookInventoryFileDataSource();
        BookInventory bookInventory = dataSource.readData();

        Book book = bookInventory.max(new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                if (o1.getName().compareTo(o2.getName()) > 0) return 1;
                if (o1.getName().compareTo(o2.getName()) < 0) return -1;
                return 0;
            }
        });

        assertEquals("แฮร์รี่ พอตเตอร์ กับเครื่องรางยมทูต เล่ม 7 ฉบับปี 2020", book.getName());
    }

    @Test
    void testMaxBookByPages(){
        DataSource<BookInventory> dataSource;
        dataSource = new BookInventoryFileDataSource();
        BookInventory bookInventory = dataSource.readData();

        Comparator<Book> pageComparator = new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                if (o1.getPages() > o2.getPages()) return 1;
                if (o1.getPages() < o2.getPages()) return -1;
                return 0;
            }
        };

        Book book = bookInventory.max(pageComparator);
        assertEquals("แฮร์รี่ พอตเตอร์ กับเครื่องรางยมทูต เล่ม 7 ฉบับปี 2020", book.getName());
    }

    @Test
    void testMaxBookByPrice(){
        DataSource<BookInventory> dataSource;
        dataSource = new BookInventoryFileDataSource();
        BookInventory bookInventory = dataSource.readData();

        Comparator<Book> priceComparator = new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                if (o1.getPrice() > o2.getPrice()) return 1;
                if (o1.getPrice() < o2.getPrice()) return -1;
                return 0;
            }
        };

        Book book = bookInventory.max(priceComparator);
        assertEquals("George's Secret Key to the Universe 1Ed.(H)", book.getName());
    }

    @Test
    void testFilterBookFromYear(){
        DataSource<BookInventory> dataSource;
        dataSource = new BookInventoryFileDataSource();
        BookInventory bookInventory = dataSource.readData();

        ArrayList<Book> filtered = bookInventory.filter(new BookFromYearFilterer(2020));
        assertEquals(6, filtered.size());
    }

    @Test
    void testConditionFilterBookFromYear(){
        DataSource<BookInventory> dataSource;
        dataSource = new BookInventoryFileDataSource();
        BookInventory bookInventory = dataSource.readData();

        ArrayList<Book> filtered = bookInventory.filter(new BookFromYearConditionFilterer(2020));
        assertEquals(6, filtered.size());
    }
}