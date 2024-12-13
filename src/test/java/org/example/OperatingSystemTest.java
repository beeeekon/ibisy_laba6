package org.example;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class OperatingSystemTest {
    @Test
    public void isWorkingOS() throws IOException, InterruptedException {
        //OperatingSystem os= new OperatingSystem();
        //os.mainMenu();
        System.out.println("lknn: ");

        String input="added gjhghgh ojoj";
        InputStream in= new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner console= new Scanner(System.in);

        while (console.hasNext()) {
            String name = console.next(); // считываем следующее слово
            System.out.println("name =" + name);
        }
    }
}