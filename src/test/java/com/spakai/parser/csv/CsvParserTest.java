/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spakai.parser.csv;

import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author prakash
 */
public class CsvParserTest {
     @Test
    public void LoadFile() {
        try {
            CsvParser<Record> parser = new CsvParser<Record>(Record.class);
            parser.load("/home/prakash/test.csv");

            for(Record record : parser) {

            } 
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
