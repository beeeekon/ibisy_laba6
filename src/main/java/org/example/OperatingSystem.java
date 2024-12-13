package org.example;

//import java.util.Collection;
//import jdk.incubator.vector.VectorMask;

import java.io.IOException;
import java.security.Permission;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class OperatingSystem {
    private class User{
        private boolean isAdmin;//является ли человек администратором
        private String name;//никнейм пользователя
        private String password;//пароль для входа в систему
        //private Vector<Boolean> permission=new Vector<Boolean>(4);//вид [чтение, запись, изменение, передача прав]
        private boolean isCorrectPassword(String password){//чтобы в пароле не было лишних символов
            boolean flag=true;
            for(int i=0;i<password.length()&&flag;i++){
                if(!(password.charAt(i)>='a'&&password.charAt(i)<='z'
                        || password.charAt(i)>='A'&&password.charAt(i)<='Z'
                        || password.charAt(i)=='@'|| password.charAt(i)=='_'|| password.charAt(i)=='!'
                        ||password.charAt(i)>='0'&&password.charAt(i)<='9'))
                    flag=false;
            }
            return flag;
        }

        private String fixPassword(String password){//чтобы пароль вводили правильный
            while(!isCorrectPassword(password)||password.length()<4){
                if(!isCorrectPassword(password))
                    System.out.println("Sorry, your password have a forbidden symbols");
                if(password.length()<4)
                    System.out.println("Password must consist of at least 4 symbols");
                System.out.println("Please!!! Enter the new password");
                Scanner console= new Scanner(System.in);
                password=console.nextLine();
            }
            return password;
        }
        private boolean matchPassword(){//проверка на знание пароля
            boolean result= false;
            String current_password="";
            System.out.println("Enter the current password");
            Scanner console= new Scanner(System.in);
            current_password=console.nextLine();
            if(!this.password.equals(current_password)) {
                System.out.println("Sorry, password is incorrect");
                result = false;
            }
            else {
                System.out.println("Thanks, password is correct");
                result = true;
            }
            return result;
        }

        public User(boolean isAdmin, String name, String password){
            this.isAdmin=isAdmin;
            this.name=name;
            this.password=fixPassword(password);
            /*if(this.isAdmin)
                Collections.addAll(this.permission, true,true,true,true);
            else
                Collections.addAll(this.permission, true,false,false,false);
                */
           // System.out.println("The authorization of the "+this.name+" was successful!");
        }

        /*public User(boolean isAdmin, String name, String password,Vector<Boolean> permission){
            this.isAdmin=isAdmin;
            this.name=name;
            this.password=fixPassword(password);
            //если ему дают права админа и тут же указывают не полные права, то все равно даются полные
            if(this.isAdmin)
                Collections.addAll(this.permission, true,true,true,true);
            else
                if(permission.size()==4)
                    this.permission=permission;
                else {
                    System.out.println("Incorrect permission information. The default permissions are set");
                    Collections.addAll(this.permission, true,false,false,false);
                }


            // System.out.println("The authorization of the "+this.name+" was successful!");
        }
        */
        protected void setAdmin(boolean adm){//дать админку может только админ
            this.isAdmin=adm;
        }
        public boolean setPassword(String newPassword){//изменить пароль
            boolean result=false;
            newPassword=fixPassword(newPassword);
            if(!matchPassword()) {
                result=false;
            }
            else{
                if(this.password!=newPassword)
                    this.password = newPassword;
                result = true;
                System.out.println("Operation successful. Password changed");
            }
            return result;
        }
        public boolean setName(String name){//изменить имя
            boolean result=false;
            if(!matchPassword()) {
                result=false;
            }
            else{
                if(this.name!=name)
                    this.name=name;
                result=true;
                System.out.println("Operation successful. Name changed");
            }
            return result;
        }


        public boolean isAdmin(){//есть ли админка
            return this.isAdmin;
        }
        public String isName(){//какое имя
            return this.name;
        }
        protected String isPassword(){//какой пароль
            return this.password;
        }
        /*public String isPermission(){//просмотр разрешений

            String exit="";
            int kolvoTrue=0;
            if(this.permission.elementAt(0)) {
                exit += "read ";
                kolvoTrue++;
            }
            if(this.permission.elementAt(0)) {
                exit += "write ";
                kolvoTrue++;
            }
            if(this.permission.elementAt(0)) {
                exit += "change ";
                kolvoTrue++;
            }
            if(this.permission.elementAt(0)) {
                exit += "transfer_rights ";
                kolvoTrue++;
            }
            if(kolvoTrue==4)
                exit="All permissions";
            if(kolvoTrue==0)
                exit="Prohibition";

            return exit;
        }
        public boolean isPermission(int index){//узнать конкретное разрешение
            if(index<=0||index>=5){//неправильное место. Разрешения всего 4
                throw new IllegalArgumentException();
            }
            return permission.get(index-1);
        }

         */
    }
    private class Permission{
        Vector<Boolean> permiss=new Vector<Boolean>(4);//вид [чтение, запись, изменение, передача прав]
        public Permission(){
            Collections.addAll(this.permiss, true,false,false,false);
        }
        public Permission(Vector<Boolean> permiss){
            if(permiss.size()==4)
                this.permiss=permiss;
            else {
                System.out.println("Incorrect permission information. The default permissions are set");
                Collections.addAll(this.permiss, true,false,false,false);
            }
        }
        protected boolean setPermission(int index, boolean value){
            boolean result =false;
            if(index<0||index>4){//неправильное место. Разрешения всего 4
                result =false;
            }
            else{
                    this.permiss.setElementAt(value,index);
                    result=true;
            }
            return result;
        }
        public String isPermission(){//просмотр разрешений
            String exit="";
            int kolvoTrue=0;
            if(this.permiss.elementAt(0)) {
                exit += "read ";
                kolvoTrue++;
            }
            if(this.permiss.elementAt(1)) {
                exit += "write ";
                kolvoTrue++;
            }
            if(this.permiss.elementAt(2)) {
                exit += "change ";
                kolvoTrue++;
            }
            if(this.permiss.elementAt(3)) {
                exit += "transfer_rights ";
                kolvoTrue++;
            }
            if(kolvoTrue==4)
                exit="All permissions";
            if(kolvoTrue==0)
                exit="Prohibition";

            return exit;
        }
        public boolean isPermission(int index){//узнать конкретное разрешение
            if(index<0||index>4){//неправильное место. Разрешения всего 4
                throw new IllegalArgumentException();
            }
            return permiss.get(index);
        }
    }
    private class Pair {
        private int firstVar;
        private User secondVar;

        public Pair(int a, User b) {
            firstVar = a;
            secondVar = b;
        }

        protected void setFirstVar(int a) {
            firstVar = a;
        }

        protected void setSecondVar(User b) {
            secondVar = b;
        }

        public int getFirstVar() {
            return firstVar;
        }

        public User getSecondVar() {
            return secondVar;
        }

    }

    Pair currentUser;//будем хранить порядковый номер юзера и самого юзера. Так быстрее выделять инфу
    Vector<User> users=new Vector<User>();
    Vector<String> files= new Vector<String>();
    Vector<Vector<Permission>> tableAccess=new Vector<Vector<Permission>>(users.size());
    //первый вектор длиной в users, вложенные векторы длиной в files
    boolean stateOS=false;//true - совершен вход от пользователя; false - ожидание входа в учетную запись
    Scanner console= new Scanner(System.in);

    private boolean isUniqueName(String name){
        boolean result=true;
        for(User currUser :users){
            result=result&&!(currUser.isName().equals(name));
        }
        return result;
    }
    private void createUser(){
        System.out.println("Created a new User: ");
        System.out.print("Name :");
        String name;
        boolean cycle=false;
        do {
            name = console.nextLine();
            if(!isUniqueName(name)){
                cycle=true;
                System.out.println("Sorry, this name already exists. Please enter other name:");
                System.out.print("Name: ");
            }
            else{
                cycle=false;
            }
        }while(cycle);
        System.out.print("Password :");
        String password=console.nextLine();
        User newUser= new User(false, name, password);
        users.add(newUser);
        tableAccess.add(new Vector<Permission>());
        for(int i=0;i<files.size();i++) {
            Vector<Boolean> perm=new Vector<Boolean>();
            Collections.addAll(perm, false,false,false,false);
            tableAccess.elementAt(users.size() - 1).add(new Permission(perm));
        }
        System.out.println("User successfully created!\n\n");
    }
    public OperatingSystem(){
        System.out.println("Hello! You have logged into the operating system for the first time!\n\n");
        System.out.println("Please, create a user with administrator rights");
        createUser();
        users.elementAt(users.size()-1).setAdmin(true);
        //System.out.println("Logged in to "+name+" account");
        stateOS=false;
        //currentUser=new Pair(0,newUser);

    }
    private void selectAccountWithoutUser() //throws IOException, InterruptedException
    {
        //печатает список для выбора юзеров
        System.out.println("\n\n\n");
        System.out.println("Which account do you want to sign in to?");
        for(int i=0;i< users.size();i++)
            System.out.println((i+1)+" - "+users.elementAt(i).isName());

    }
    private void selectActionWithoutUser(){//печатает остаток выбора дейтсвий без текущего юзера
        int startingIndex= users.size()+1;
        System.out.println();
        System.out.println(startingIndex+" - create a new user");
        startingIndex++;
        System.out.println(startingIndex+" - exit the OS");
    }
    private int mainMenuWithoutUser(){// проверяет какое действие выбрали без текущего юзера

        selectAccountWithoutUser();
        selectActionWithoutUser();
        int input;
        System.out.println();
        System.out.print("Your selected option: ");
        input=console.nextInt();
        if(input>=1&&input<=indexExitWithoutUser())
            return input;
        else
            return -1;
    }

    private int indexExitWithoutUser(){//когда находятся на общем окне выбора учеток, последний вариант - выход
        return users.size() + 2;//кол-во юзеров + две свободные операции
    }
    private void analyticInputWithoutUser(int input){
        if(input>=1&&input<=users.size())
            accountLogin(input-1);//вход в аккаунт
        else{
            if(input==users.size()+1)
                createUser();
        }
    }
    private void accountLogin(int index){
        if(stateOS)//если мы вызвали эту функцию будучи уже вошедшими в аккаунт, это не правильно
            throw new IllegalArgumentException();
        User currUser=users.elementAt(index);
        System.out.println("Login to "+currUser.isName()+" account");
        if(currUser.matchPassword()){
            currentUser=new Pair(index,currUser);
            stateOS=true;
            System.out.println();
            System.out.println("Welcome, "+currUser.isName());
        }
    }

    private void selectActionAdmin(){
        System.out.println("CURRENT USER: "+currentUser.secondVar.isName());
        System.out.println();
        System.out.println("1 - view information about the users OS");
        System.out.println("2 - view file system permissions table");
        System.out.println();
        System.out.println("3 - inspect the file system");
        System.out.println("4 - create a new file");
        System.out.println();

        System.out.println("5 - edit this account");
        System.out.println("6 - delete this account");
        System.out.println("7 - change user");
        System.out.println("8 - exit the OS");
    }
    private int indexExitWithAdmin(){
        return 8;
    }
    private void selectActionNotAdmin(){
        System.out.println("CURRENT USER: "+currentUser.secondVar.isName());
        System.out.println();
        System.out.println("1 - inspect the file system");
        System.out.println("2 - create a new file");
        System.out.println();

        System.out.println("3 - edit this account");
        System.out.println("4 - delete this account");
        System.out.println("5 - change user");
        System.out.println("6 - exit the OS");
    }
    private int indexExitWithNotAdmin(){
        return 6;
    }
    private int mainMenuWithUser(){
        System.out.println("\n\n\n");
        if(currentUser.getSecondVar().isAdmin){//если текущий юзер админ
            selectActionAdmin();
            int input;
            System.out.println();
            System.out.print("Your selected option: ");
            input=console.nextInt();
            if(input>=1&&input<=indexExitWithAdmin())
                return input;
            else
                return -1;
        }
        else{//если текущий юзер не админ
            selectActionNotAdmin();
            int input;
            System.out.println();
            System.out.print("Your selected option: ");
            input=console.nextInt();
            if(input>=1&&input<=indexExitWithNotAdmin())
                return input;
            else
                return -1;
        }
    }
    private int informationUsersOS(){//вывод информации о юзерах для админа и выбор варианта
        int input;
        for(int i=0;i<users.size();i++){
            System.out.println((i+1)+" - "+users.elementAt(i).isName()
                    +"  password:"+users.elementAt(i).isPassword()
                    + "    isAdmin: "+ users.elementAt(i).isAdmin());
        }
        System.out.println();
        System.out.println("If you want to edit user information, enter his number");
        System.out.println("0 - return to menu");
        System.out.print("Your selected option: ");
        input=console.nextInt();
        if(input<0||input>users.size())
            return -1;
        else
            return input;
    }
    private int whatChange(){
        int input;
        System.out.println("\n");
        System.out.println("1 - edit name");
        System.out.println("2 - edit password");
        System.out.println("3 - appoint as admin");
        System.out.println("4 - delete account");
        System.out.print("Your selected option: ");
        input=console.nextInt();
        if(input>=1&&input<=4)
            return input;
        else
            return -1;
    }
    private void editNameUser(int index){
        String newName;
        System.out.println("\n");
        System.out.println("Current name: "+users.elementAt(index).isName());
        boolean cycle=false;
        do {
            System.out.print("New name: ");
            newName = console.nextLine();
            if(!isUniqueName(newName)){
                System.out.println();
                System.out.println("Sorry, such a name already exists in the system. Enter a different name");
                cycle=true;
            }
        }while(cycle);
        users.elementAt(index).setName(newName);
        System.out.println("name successfully edited!");
    }
    private void editPasswordUser(int index){
        System.out.println("\n");
        System.out.println("Current password: "+users.elementAt(index).isPassword());
        System.out.print("New password: ");
        String newPas;
        newPas=console.nextLine();
        users.elementAt(index).setPassword(newPas);

    }
    private void editSetAdmin(int index){
        users.elementAt(index).setAdmin(true);
        for(int i=0;i<files.size();i++) {
            tableAccess.elementAt(index).elementAt(i).setPermission(0, true);
            tableAccess.elementAt(index).elementAt(i).setPermission(1, true);
            tableAccess.elementAt(index).elementAt(i).setPermission(2, true);
            tableAccess.elementAt(index).elementAt(i).setPermission(3, true);
        }
        System.out.println("\n");
        System.out.print("User "+users.elementAt(index).isName()+" is assigned as admin");
    }
    private void editDeleteAccount(int index){
        String name =users.elementAt(index).isName();
        users.remove(index);
        tableAccess.remove(index);
        System.out.println("\n");
        System.out.print(name+"'s account has been deleted");
    }
    private void editUserData(int index){//работает с конкретным юзером index
        if(users.elementAt(index).isAdmin())
            System.out.println("Sorry, you cannot change admin details");
        else{
            boolean cycle=false;
            int input;
            do{
                input= whatChange();
                if(input==-1){
                    System.out.println("\n\n");
                    System.out.println("PLEASE choose one of the OFFERED options!!!!!!");
                    cycle=true;
                }
                else{
                    cycle=false;
                }
            }while(cycle);
            console.nextLine();//иначе пропускает строку
            switch(input){
                case 1:{//изменение имени
                    editNameUser(index);
                    break;
                }
                case 2:{//изменение пароля
                    editPasswordUser(index);
                    break;
                }
                case 3:{//назначение админом
                    editSetAdmin(index);
                    break;
                }
                case 4:{//удалить аккаунт
                    editDeleteAccount(index);
                    break;
                }
            }
        }
    }
    private void viewInformationAboutUsersOS(){
        boolean cycle=false;
        int input;
        do{
            input= informationUsersOS();
            if(input==-1){
                System.out.println("\n\n");
                System.out.println("PLEASE choose one of the OFFERED options!!!!!!");
                cycle=true;
            }
            else
                cycle=false;
        }while(cycle);
        if(input>=1&&input<=users.size()){
            editUserData(input-1);
        }
        //если инпут был 0 то мы ничего не делаем и просто возвращаемся в главное меню
    }
    private int permissionsTable(){
        if(tableAccess.elementAt(0).size()==0){//берем рандомного юзера и смотрим сколько файлов у него есть
            System.out.println("No files have been created yet\n");
            return 0;
        }
        else{
            for(int j=0;j<files.size();j++){//идем по файлам
                System.out.println("\n          "+(j+1)+" - FILE: "+files.elementAt(j));
                for(int i=0;i<tableAccess.size();i++){//идем по юзерам
                    System.out.println((i+1)+" - "+users.elementAt(i).isName()+" : "+
                            tableAccess.elementAt(i).elementAt(j).isPermission());
                }
            }

            System.out.println("\n If you want to change permissions for a file, enter its number");
            System.out.println("0 - return to menu");
            System.out.print("Your selected option: ");
            int input;
            input=console.nextInt();
            if(input<0||input>users.size())
                return -1;
            else
                return input;
        }
    }
    private int permissionFile(int indexFile){
        System.out.println("\n           "+(indexFile+1)+" - FILE: "+files.elementAt(indexFile));
        for(int i=0;i<tableAccess.size();i++){//идем по юзерам
            System.out.println((i+1)+" - "+users.elementAt(i).isName()+" : "+
                    tableAccess.elementAt(i).elementAt(indexFile).isPermission());
        }
        System.out.println("\n If you want to change user permissions, enter his number");
        System.out.println("0 - return to menu");
        System.out.print("Your selected option: ");
        int input;
        input=console.nextInt();
        if(input<0||input>users.size())
            return -1;
        else
            return input;
    }
    private void editUserPermission(int indexFile, int indexUser){
        System.out.println("\n\n\n"+(indexFile+1)+" - FILE: "+files.elementAt(indexFile));
        System.out.println((indexUser+1)+" - "+users.elementAt(indexUser).isName()+" : "+
                tableAccess.elementAt(indexUser).elementAt(indexFile).isPermission());
        boolean cycle=false;
        int input;
        System.out.println("\n");
        do {
            System.out.println("What user permission do you want to change?");
            System.out.println("1 - read");
            System.out.println("2 - write");
            System.out.println("3 - change");
            System.out.println("4 - transfer_rights");
            System.out.print("Your selected option: ");
            input=console.nextInt();
            if(input<1||input>4)
                input= -1;
            if(input==-1){
                System.out.println("\n\n");
                System.out.println("PLEASE choose one of the OFFERED options!!!!!!");
                cycle=true;
            }
            else
                cycle=false;
        }while(cycle);
        tableAccess.elementAt(indexUser).elementAt(indexFile).
                setPermission(input-1,!tableAccess.elementAt(indexUser).elementAt(indexFile).isPermission(input-1));
        System.out.println("\n User permission changed successfully!!");

    }
    private void editFilePermission(int indexFile){
        boolean cycle=false;
        int input;
        do{
            input= permissionFile(indexFile);
            if(input==-1){
                System.out.println("\n\n");
                System.out.println("PLEASE choose one of the OFFERED options!!!!!!");
                cycle=true;
            }
            else
                cycle=false;
        }while(cycle);
        if(input>=1&&input<= users.size()){
            if(users.elementAt(input-1).isAdmin())
                System.out.println("Sorry, you cannot edit access permissions for the admin");
            else
                editUserPermission(indexFile, input-1);
        }
        //если инпут был 0 то мы ничего не делаем и просто возвращаемся в главное меню
    }

    private void viewFileSystemPermissionsTable(){
        boolean cycle=false;
        int input;
        do{
            input= permissionsTable();
            if(input==-1){
                System.out.println("\n\n");
                System.out.println("PLEASE choose one of the OFFERED options!!!!!!");
                cycle=true;
            }
            else
                cycle=false;
        }while(cycle);
        if(input>=1&&input<= files.size()){
            editFilePermission(input-1);
        }
        //если инпут был 0 то мы ничего не делаем и просто возвращаемся в главное меню
    }

    private int viewFileSystem(){
        System.out.println("\n\n File system:");
        for(int i=0;i<files.size();i++){
            System.out.println((i+1)+" - file: "+files.elementAt(i));
        }
        System.out.println("\n if you want to interact with a file, enter its number");
        System.out.println("0 - return to menu");
        System.out.print("Your selected option: ");
        int input;
        input=console.nextInt();
        if(input<0||input>files.size())
            return -1;
        else
            return input;
    }
    private void transferRights(int indexFile){
        boolean cycle=false;
        int input;
        System.out.println("\n");
        do {
            System.out.println("\n          "+(indexFile+1)+"- FILE: "+files.elementAt(indexFile));
            System.out.println("\n What permission do you want to pass?");
            System.out.println("1 - read");
            System.out.println("2 - write");
            System.out.println("3 - change");
            System.out.println("4 - transfer_rights");
            System.out.print("Your selected option: ");
            input=console.nextInt();
            if(input<1||input>4)
                input= -1;
            if(input==-1){
                System.out.println("\n\n");
                System.out.println("PLEASE choose one of the OFFERED options!!!!!!");
                cycle=true;
            }
            else
                cycle=false;
        }while(cycle);
        int right=input;
        if(tableAccess.elementAt(currentUser.firstVar).elementAt(indexFile).isPermission(input-1)){
            cycle=false;
            System.out.println("\n");
            do {
                System.out.println("\n Who do you want to give permission to?");
                for(int i=0;i<users.size();i++)
                    System.out.println((i+1)+" - "+users.elementAt(i).isName());
                System.out.print("Your selected option: ");
                input=console.nextInt();
                if(input<1||input>users.size())
                    input= -1;
                if(input==-1){
                    System.out.println("\n\n");
                    System.out.println("PLEASE choose one of the OFFERED options!!!!!!");
                    cycle=true;
                }
                else
                    cycle=false;
            }while(cycle);
            tableAccess.elementAt(input-1).elementAt(indexFile).setPermission(right-1,true);
            System.out.println("\n Rights successfully transferred");
        }
        else
            System.out.println("Sorry, you do not have permission for this operation");
    }

    private void interactFile(int indexFile){
        boolean cycle=false;
        int input;
        System.out.println("\n");
        do {
            System.out.println("What do you want to do with the file: "+files.elementAt(indexFile));
            System.out.println("1 - read");
            System.out.println("2 - write");
            System.out.println("3 - change");
            System.out.println("4 - transfer_rights");
            System.out.print("Your selected option: ");
            input=console.nextInt();
            if(input<1||input>4)
                input= -1;
            if(input==-1){
                System.out.println("\n\n");
                System.out.println("PLEASE choose one of the OFFERED options!!!!!!");
                cycle=true;
            }
            else
                cycle=false;
        }while(cycle);
        if(input==1||input==2){
            if(tableAccess.elementAt(currentUser.firstVar).elementAt(indexFile).isPermission(input-1))
                System.out.println("Operation completed successfully");
            else
                System.out.println("Sorry, you do not have permission for this operation");
        }
        if(input==3){
            if(tableAccess.elementAt(currentUser.firstVar).elementAt(indexFile).isPermission(input-1)) {
                cycle = false;
                System.out.println("\n");
                do {
                    System.out.println("What do you want to do with the file: " + files.elementAt(indexFile));
                    System.out.println("1 - change file name");
                    System.out.println("2 - delete file");
                    System.out.print("Your selected option: ");
                    input = console.nextInt();
                    if (input < 1 || input > 2)
                        input = -1;
                    if (input == -1) {
                        System.out.println("\n\n");
                        System.out.println("PLEASE choose one of the OFFERED options!!!!!!");
                        cycle = true;
                    } else
                        cycle = false;
                } while (cycle);
                if (input == 1) {
                    System.out.print("New name file:");

                    String name;
                    name = console.nextLine();//иначе не считывает строку
                    cycle = false;
                    do {
                        name = console.nextLine();
                        if (name == files.elementAt(indexFile)) {
                            cycle = true;
                            System.out.println("sorry this name is the same as the current file name");
                            System.out.print("New name file: ");
                        } else if (!isUniqueFileName(name)) {
                            cycle = true;
                            System.out.println("Sorry, this file already exists. Please enter other file name:");
                            System.out.print("New name file: ");
                        } else {
                            cycle = false;
                        }
                    } while (cycle);
                    files.set(indexFile, name);
                    System.out.println("File name successfully changed");
                    return;
                }
                if (input == 2) {
                    deleteFile(indexFile);
                }
            }
            else
                System.out.println("Sorry, you do not have permission for this operation");
        }
        if(input==4){
            if(tableAccess.elementAt(currentUser.firstVar).elementAt(indexFile).isPermission(input-1)){
                transferRights(indexFile);
            }
            else
                System.out.println("Sorry, you do not have permission for this operation");
        }
    }
    private void deleteFile(int indexFile){
        for(int i=0;i<users.size();i++)
            tableAccess.elementAt(i).remove(indexFile);
        files.remove(indexFile);
        System.out.println("File deleted successfully");
    }
    private boolean isUniqueFileName(String name){
        boolean result=true;
        for(int i=0;i<files.size()&&result;i++)
            result=result&&!(files.elementAt(i).equals(name));

        return result;
    }
    private void createFile(){
        System.out.println("Created a new File: ");
        System.out.print("Name file:");
        String name;
        boolean cycle=false;
        do {
            name = console.nextLine();
            if(!isUniqueFileName(name)){
                cycle=true;
                System.out.println("Sorry, this file already exists. Please enter other file name:");
                System.out.print("Name file: ");
            }
            else{
                cycle=false;
            }
        }while(cycle);
        files.add(name);
        for(int i=0;i<users.size();i++){
            Vector<Boolean> perm=new Vector<Boolean>();
            if(users.elementAt(i).isAdmin())//админу все права
                Collections.addAll(perm, true,true,true,true);
            else {
                if (i == currentUser.firstVar)//тому кто создает все права
                    Collections.addAll(perm, true, true, true, true);
                else//остальным только чтение
                    Collections.addAll(perm, true, false, false, false);
            }
            tableAccess.elementAt(i).add(new Permission(perm));
        }
        System.out.println("File successfully created");
    }
    private void inspectFileSystem(){
        boolean cycle=false;
        int input;
        do{
            input= viewFileSystem();
            if(input==-1){
                System.out.println("\n\n");
                System.out.println("PLEASE choose one of the OFFERED options!!!!!!");
                cycle=true;
            }
            else
                cycle=false;
        }while(cycle);
        if(input>=1&&input<= files.size()){
            interactFile(input-1);
        }
        //если инпут был 0 то мы ничего не делаем и просто возвращаемся в главное меню
    }
    private void editThisAccount(){
        boolean cycle=false;
        int input;
        System.out.println("\n");
        do {

            System.out.println("What would you like to do with this account");
            System.out.println("1 - edit name");
            System.out.println("2 - edit password");
            System.out.print("Your selected option: ");
            input=console.nextInt();
            if(input<1||input>2)
                input= -1;
            if(input==-1){
                System.out.println("\n\n");
                System.out.println("PLEASE choose one of the OFFERED options!!!!!!");
                cycle=true;
            }
            else
                cycle=false;
        }while(cycle);
        if(input==1){//смена имени
            console.nextLine();//прохлопывает ввод
            editNameUser(currentUser.firstVar);
            currentUser.secondVar=users.elementAt(currentUser.firstVar);
        }
        if(input==2) {
            console.nextLine();//прохлопывает ввод
            editPasswordUser(currentUser.firstVar);
        }
    }
    private void deleteThisAccount(){
        if(currentUser.secondVar.isAdmin()) {
            int kolvoAdmin = 0;
            for (int i = 0; i < users.size(); i++)
                if (users.elementAt(i).isAdmin())
                    kolvoAdmin++;
            if (kolvoAdmin > 1) {
                editDeleteAccount(currentUser.firstVar);
                stateOS = false;
            } else
                System.out.println("You cannot delete the account of the only admin in the system");
        }
        else{
            editDeleteAccount(currentUser.firstVar);
            stateOS = false;
        }
    }
    private void analyticInputWithAdmin(int input){
        switch(input){
            case 1:{//просмотр информации о юзерах
                viewInformationAboutUsersOS();
                break;
            }
            case 2:{//просмотр таблицы разрешений файлов
                viewFileSystemPermissionsTable();
                break;
            }
            case 3:{//действия с файловой системой
                inspectFileSystem();
                break;
            }
            case 4:{//создать файл
                createFile();
                break;
            }
            case 5:{//изменение данных текущего аккаунта
                editThisAccount();
                break;
            }
            case 6:{//удалить этот аккаунт
                deleteThisAccount();
                break;
            }
            case 7:{//сменить аккаунт
                stateOS=false;
                break;
            }
        }
    }
    private void analyticInputWithNotAdmin(int input){
        switch(input){
            case 1:{//действия с файловой системой
                inspectFileSystem();
                break;
            }
            case 2:{//создать файл
                createFile();
                break;
            }
            case 3:{//изменение данных текущего аккаунта
                editThisAccount();
                break;
            }
            case 4:{//удалить этот аккаунт
                deleteThisAccount();
                break;
            }
            case 5:{//сменить аккаунт
                stateOS=false;
                break;
            }
        }
    }
    public void mainMenu() //throws IOException, InterruptedException
    {
        boolean continuationWorkOS=true;
        while(continuationWorkOS){//пока человек не нажмет нужную кнопку (кнопку выхода)
            int indexExit;
            if(!this.stateOS) //если нет текущего юзера
                indexExit=indexExitWithoutUser();
            else{
                if(currentUser.secondVar.isAdmin())//если юзер админ
                    indexExit=indexExitWithAdmin();
                else//не админ
                    indexExit=indexExitWithNotAdmin();
            }
            boolean cycle=false;
            int input;
            do{
                if(!this.stateOS)//без юзера
                    input=mainMenuWithoutUser();
                else//с юзером
                    input=mainMenuWithUser();
                if(input==-1){
                    System.out.println("\n\n");
                    System.out.println("PLEASE choose one of the OFFERED options!!!!!!");
                    cycle=true;
                }
                else
                    cycle=false;
            }while(cycle);
            if(input==indexExit)
                continuationWorkOS=false;
            else{
                System.out.println();
                console.nextLine();//иначе считывает enter и прохлопывает ввод
                if(!this.stateOS)//без юзеров
                    analyticInputWithoutUser(input);
                else{
                    if(currentUser.secondVar.isAdmin())//для админа
                        analyticInputWithAdmin(input);
                    else//для не админа
                        analyticInputWithNotAdmin(input);
                }
            }
        }
    }
}
