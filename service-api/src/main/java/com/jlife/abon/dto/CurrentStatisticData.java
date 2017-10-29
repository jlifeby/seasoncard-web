package com.jlife.abon.dto;

/**
 * @author Dzmitry Misiuk
 */
public class CurrentStatisticData {

    private int hourAttendances;
    private int hourAbonnements;
    private int hourSingleAttendances;
    private double hourAbonnementsSum;
    private double hourSingleAttendancesSum;


    private int todayAttendances;
    private int todayAbonnements;
    private int todaySingleAttendances;
    private double todayAbonnementsSum;
    private double todaySingleAttendancesSum;

    public int getHourAttendances() {
        return hourAttendances;
    }

    public void setHourAttendances(int hourAttendances) {
        this.hourAttendances = hourAttendances;
    }

    public int getHourAbonnements() {
        return hourAbonnements;
    }

    public void setHourAbonnements(int hourAbonnements) {
        this.hourAbonnements = hourAbonnements;
    }

    public double getHourAbonnementsSum() {
        return hourAbonnementsSum;
    }

    public void setHourAbonnementsSum(double hourAbonnementsSum) {
        this.hourAbonnementsSum = hourAbonnementsSum;
    }

    public int getTodayAttendances() {
        return todayAttendances;
    }

    public void setTodayAttendances(int todayAttendances) {
        this.todayAttendances = todayAttendances;
    }

    public int getTodayAbonnements() {
        return todayAbonnements;
    }

    public void setTodayAbonnements(int todayAbonnements) {
        this.todayAbonnements = todayAbonnements;
    }

    public double getTodayAbonnementsSum() {
        return todayAbonnementsSum;
    }

    public void setTodayAbonnementsSum(double todayAbonnementsSum) {
        this.todayAbonnementsSum = todayAbonnementsSum;
    }

    public int getHourSingleAttendances() {
        return hourSingleAttendances;
    }

    public void setHourSingleAttendances(int hourSingleAttendances) {
        this.hourSingleAttendances = hourSingleAttendances;
    }

    public double getHourSingleAttendancesSum() {
        return hourSingleAttendancesSum;
    }

    public void setHourSingleAttendancesSum(double hourSingleAttendancesSum) {
        this.hourSingleAttendancesSum = hourSingleAttendancesSum;
    }

    public int getTodaySingleAttendances() {
        return todaySingleAttendances;
    }

    public void setTodaySingleAttendances(int todaySingleAttendances) {
        this.todaySingleAttendances = todaySingleAttendances;
    }

    public double getTodaySingleAttendancesSum() {
        return todaySingleAttendancesSum;
    }

    public void setTodaySingleAttendancesSum(double todaySingleAttendancesSum) {
        this.todaySingleAttendancesSum = todaySingleAttendancesSum;
    }
}
