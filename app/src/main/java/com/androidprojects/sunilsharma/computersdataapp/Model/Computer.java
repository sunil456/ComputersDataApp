package com.androidprojects.sunilsharma.computersdataapp.Model;

/**
 * Created by sunil sharma on 10/26/2017.
 */

public class Computer
{
    private String computerName;
    private int computerPower;
    private int computerSpeed;
    private int computerRam;
    private String computerKeyboard;
    private String computerCPU;
    private String computerScreen;

    public Computer()
    {
    }

    public Computer(String computerName, int computerPower, int computerSpeed, int computerRam,
                    String computerKeyboard, String computerCPU, String computerScreen)
    {
        this.computerName = computerName;
        this.computerPower = computerPower;
        this.computerSpeed = computerSpeed;
        this.computerRam = computerRam;
        this.computerKeyboard = computerKeyboard;
        this.computerCPU = computerCPU;
        this.computerScreen = computerScreen;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public int getComputerPower() {
        return computerPower;
    }

    public void setComputerPower(int computerPower) {
        this.computerPower = computerPower;
    }

    public int getComputerSpeed() {
        return computerSpeed;
    }

    public void setComputerSpeed(int computerSpeed) {
        this.computerSpeed = computerSpeed;
    }

    public int getComputerRam() {
        return computerRam;
    }

    public void setComputerRam(int computerRam) {
        this.computerRam = computerRam;
    }

    public String getComputerKeyboard() {
        return computerKeyboard;
    }

    public void setComputerKeyboard(String computerKeyboard) {
        this.computerKeyboard = computerKeyboard;
    }

    public String getComputerCPU() {
        return computerCPU;
    }

    public void setComputerCPU(String computerCPU) {
        this.computerCPU = computerCPU;
    }

    public String getComputerScreen() {
        return computerScreen;
    }

    public void setComputerScreen(String computerScreen) {
        this.computerScreen = computerScreen;
    }
}
