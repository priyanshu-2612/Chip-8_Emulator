package main.java;

import java.util.Arrays;
import java.util.Random;

public class InstructionSet {
    private Display display;
    private Memory memory;
    private Keyboard keyboard;
    private int pressedKey;

    public InstructionSet(Memory memory, Display display, Keyboard keyboard) {
        this.memory = memory;
        this.display = display;
        this.keyboard = keyboard;
        pressedKey = -1;
    }

    public void cls(){
        System.out.println("CLEAR command");
        for(int i=0 ; i<display.HEIGHT ; i++)
            Arrays.fill(display.displayscreen[i],0);
        display.render();
        memory.PC += 2;
    }
    public void ret(){
        System.out.println("Return");
        memory.PC = memory.stack[memory.SP];
        memory.SP--;
        memory.PC += 2;
        System.out.println("Stack Pointer is " + memory.SP+ " Program Counter is "+ memory.PC);
    }
    public void jmp(String n){
        System.out.println("Jumping to "+ (short)Integer.parseInt(n,16) + " Currently at " + Short.toUnsignedInt(memory.PC));
        //if((short)Integer.parseInt(n,16)==Short.toUnsignedInt(memory.PC))memory.PC = (short) (Integer.parseInt(n,16)+2);
        //else
        memory.PC = (short)Integer.parseInt(n,16);
    }
    public void call(String n){
        System.out.println("Call");
        memory.SP++;
        memory.stack[memory.SP] = memory.PC;
        memory.PC = (short)Integer.parseInt(n,16);
        System.out.println("Stack Pointer is " + memory.SP);
    }
    public void skipIfEqual(String register , short kk){
        System.out.println("Reg is "+Integer.parseInt(register,16) + " and its value is "+ (memory.V[Integer.parseInt(register,16)]) + " kk is " + Short.toUnsignedInt(kk));
        if(Byte.toUnsignedInt(memory.V[Integer.parseInt(register,16)])==Short.toUnsignedInt(kk)) memory.PC += 4;
        else memory.PC += 2;
    }
    public void skipIfNotEqual(String register , short kk){
        System.out.println("Reg value is "+Byte.toUnsignedInt(memory.V[Integer.parseInt(register,16)])+" is " + Short.toUnsignedInt(kk));
        if(Byte.toUnsignedInt(memory.V[Integer.parseInt(register,16)])!=Short.toUnsignedInt(kk)) memory.PC += 4;
        else memory.PC += 2;
    }
    public void skipIfRegistersAreEqual(String a , String b){
        if(memory.V[Integer.parseInt(a,16)]==memory.V[Integer.parseInt(b,16)]) memory.PC += 4;
        else memory.PC += 2;
    }
    public void load(String register , byte val){
        System.out.println("Put "+ Byte.toUnsignedInt(val) + " in Reg " + Integer.parseInt(register,16));
        memory.V[Integer.parseInt(register,16)] = val;
        memory.PC += 2;
    }
    public void add(String register , byte val){
        memory.V[Integer.parseInt(register,16)] += val;
        memory.PC += 2;
    }
    public void set(String registerA , String registerB){
        System.out.println("Set Reg "+ Integer.parseInt(registerA,16) + " value to " + memory.V[Integer.parseInt(registerB,16)]);
        memory.V[Integer.parseInt(registerA,16)] = memory.V[Integer.parseInt(registerB,16)];
        memory.PC += 2;
    }
    public void or(String regA , String regB){
        int a = Byte.toUnsignedInt(memory.V[Integer.parseInt(regA,16)]),
                b = Byte.toUnsignedInt(memory.V[Integer.parseInt(regB,16)]);
        memory.V[Integer.parseInt(regA,16)] = (byte) (a | b);
        memory.V[0xf] = 0;
        memory.PC += 2;
    }
    public void and(String regA , String regB){
        int a = Byte.toUnsignedInt(memory.V[Integer.parseInt(regA,16)]),
                b = Byte.toUnsignedInt(memory.V[Integer.parseInt(regB,16)]);
        memory.V[Integer.parseInt(regA,16)] = (byte) (a & b);
        memory.V[0xf] = 0;
        memory.PC += 2;
    }
    public void xor(String regA , String regB){
        int a = Byte.toUnsignedInt(memory.V[Integer.parseInt(regA,16)]),
                b = Byte.toUnsignedInt(memory.V[Integer.parseInt(regB,16)]);
        memory.V[Integer.parseInt(regA,16)] = (byte) (a ^ b);
        memory.V[0xf] = 0;
        memory.PC += 2;
    }
    public void add(String regA , String regB){
        int sum = Byte.toUnsignedInt(memory.V[Integer.parseInt(regA,16)]) +
                Byte.toUnsignedInt(memory.V[Integer.parseInt(regB,16)]);
        memory.V[Integer.parseInt(regA,16)] += memory.V[Integer.parseInt(regB,16)];
        if(sum>255) memory.V[0xF] = 1;
        else memory.V[0xF] = 0;
        System.out.println("Reg Vf is "+ memory.V[0xf]);
        memory.PC += 2;
    }
    public void sub(String regA , String regB){
        int a = Byte.toUnsignedInt(memory.V[Integer.parseInt(regA,16)]) ,
                b = Byte.toUnsignedInt(memory.V[Integer.parseInt(regB,16)]);
        memory.V[Integer.parseInt(regA,16)] -= memory.V[Integer.parseInt(regB,16)];
        if(a>=b) memory.V[0xF] = 1;
        else memory.V[0XF] = 0;
        System.out.println("A is "+a + " B is "+ b + " Vf is "+ memory.V[0xF]);
        memory.PC += 2;
    }
    public void shr(String regA , String regB){
        int a = Byte.toUnsignedInt(memory.V[Integer.parseInt(regA,16)]);
        System.out.println("Shifting "+ a+ "  to the right");
        memory.V[Integer.parseInt(regA,16)] = (byte) (a>>1);   //>> shifts with 1/0 depending on -/+ while >>> shifts using 0 always
        if(a%2==1) memory.V[0xF] = 1;
        else memory.V[0xF] =0;
        System.out.println("Reg Vf is "+ memory.V[0xf]);
        memory.PC += 2;
    }
    public void subn(String regA , String regB){
        int a = Byte.toUnsignedInt(memory.V[Integer.parseInt(regA,16)]) ,
                b = Byte.toUnsignedInt(memory.V[Integer.parseInt(regB,16)]);
        memory.V[Integer.parseInt(regA,16)] = (byte) (memory.V[Integer.parseInt(regB,16)]-memory.V[Integer.parseInt(regA,16)]);
        if(b>=a) memory.V[0xF] = 1;
        else memory.V[0xF] = 0;
        System.out.println("Reg Vf is "+ memory.V[0xf]);
        memory.PC += 2;
    }
    public void shl(String regA , String regB){
        int a = Byte.toUnsignedInt(memory.V[Integer.parseInt(regA,16)]);
        memory.V[Integer.parseInt(regA,16)] = (byte) (a << 1);
        if(a/128 == 1) memory.V[0xF] = 1;
        else memory.V[0xF] =0;
        System.out.println("Reg Vf is "+ memory.V[0xf]);
        System.out.println("Value is now " + Byte.toUnsignedInt(memory.V[Integer.parseInt(regA,16)]));
        memory.PC += 2;
    }
    public void sne(String regA , String regB){
        int a = Byte.toUnsignedInt(memory.V[Integer.parseInt(regA,16)]) ,
                b = Byte.toUnsignedInt(memory.V[Integer.parseInt(regB,16)]);
        if(a!=b) memory.PC += 4;
        else memory.PC += 2;
    }
    public void ldi(String n){
        memory.I = (short)Integer.parseInt(n,16);
        memory.PC += 2;
        System.out.println("Inst reg is now " + memory.I);
    }
    public void jmpv0(String n){
        int off = Integer.parseInt(n,16),
                b = Byte.toUnsignedInt(memory.V[0]);
        memory.PC = (short) (b+off);
    }
    public void rndvx(String regA, String kk){
        Random r = new Random();
        byte[] b = new byte[1];
        r.nextBytes(b);
        memory.V[Integer.parseInt(regA,16)] = (byte) ((byte)Integer.parseInt(kk,16) & b[0]);
        memory.PC += 2;
    }
    public void drw(String regA , String regB , int n){
        int x = Byte.toUnsignedInt(memory.V[Integer.parseInt(regA,16)]),
                    y = Byte.toUnsignedInt(memory.V[Integer.parseInt(regB,16)]);

        memory.V[0xf] = 0;
        for(int i=0 ; i<n ; i++) {
            int row = Byte.toUnsignedInt(memory.ram[memory.I + i]);
            System.out.println(Integer.toBinaryString(row));
            System.out.println("Vx is " + x + " Vy is "+y + " n is "+n);
            for (int j = 0; j < 8; j++) {
                if ((row & (0x80 >> j)) != 0) {
                    if (display.displayscreen[(x+j) % 64][(y+i) % 32]==1) memory.V[0xF] = 1;
                    display.displayscreen[(x+j) % 64][(y+i) % 32] ^= 1;
                }
            }
        }
        display.render();
        memory.PC += 2;
    }
    public void skpvx(String regA){
        int a = Integer.parseInt(regA,16);
        System.out.println("Key at "+ a + " is " + String.valueOf(memory.V[a]));
        if(keyboard.key[Integer.parseInt(String.valueOf(memory.V[a]))]==1)memory.PC += 4;
        else memory.PC += 2;
    }
    public void sknpvx(String regA){
        int a = Integer.parseInt(regA,16);
        if(keyboard.key[Integer.parseInt(String.valueOf(memory.V[a]))]!=1)memory.PC += 4;
        else memory.PC += 2;
    }
    public void ldvx(String regA){
        int a = Integer.parseInt(regA,16);
        memory.V[a] = memory.delayTimer;
        System.out.println("reg a is now " + Byte.toUnsignedInt(memory.V[a]));
        memory.PC += 2;
    }
    public void ldvxk(String regA){
        int a = Integer.parseInt(regA,16);
        if(pressedKey!=-1 && keyboard.key[pressedKey]==0){  //Key is released
            memory.V[a] = (byte) pressedKey;
            memory.PC += 2;
            pressedKey = -1;
            return;
        }
            for (int j = 0; j <= 0xF; j++) {
                if (keyboard.key[j] == 1) {
                        pressedKey = j;
                        return;
                }
            }
    }
    public void lddt(String regA){
        memory.delayTimer = memory.V[Integer.parseInt(regA,16)];
        memory.PC += 2;
    }
    public void ldst(String regA){
        memory.soundTimer = memory.V[Integer.parseInt(regA,16)];
        memory.PC += 2;
    }
    public void addi(String regA){
        System.out.println("Adding " + Byte.toUnsignedInt(memory.V[Integer.parseInt(regA,16)]) + " to Inst reg");
        memory.I = (short)(Byte.toUnsignedInt(memory.V[Integer.parseInt(regA,16)]) + memory.I);
        System.out.println("Inst reg is now " + Short.toUnsignedInt(memory.V[Integer.parseInt(regA,16)]));
        memory.PC += 2;
    }
    public void ldfvx(String regA){
        int a = Byte.toUnsignedInt(memory.V[Integer.parseInt(regA,16)]);
        memory.I = (short) (memory.FONT_START_ADDRESS + 5*a);
        memory.PC += 2;
    }
    public void ldb(String regA){
        int a = Byte.toUnsignedInt(memory.V[Integer.parseInt(regA,16)]);
        memory.ram[memory.I+2] = (byte) (a%10);
        a = a/10;
        memory.ram[memory.I+1] = (byte) (a%10);
        a = a/10;
        memory.ram[memory.I] = (byte) (a%10);
        memory.PC += 2;
    }
    public void ldivx(String regA){
        int a = Integer.parseInt(regA,16);
        for(int i=0 ; i<=a ; i++){
            int val = Byte.toUnsignedInt(memory.V[i]);
            memory.ram[memory.I + i] = (byte) val;
        }
        memory.I++;
        memory.PC += 2;
    }
    public void ldvxi(String regA){
        int a = Integer.parseInt(regA,16);
        for(int i=0 ; i<=a ; i++){
            int val = Byte.toUnsignedInt(memory.ram[memory.I+i]);
            memory.V[i] = (byte) val;
        }
        memory.I++;
        memory.PC += 2;
    }
}
