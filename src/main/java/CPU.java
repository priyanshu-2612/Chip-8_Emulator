package main.java;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CPU {
    private Decode decoder;
    private Memory memory;
    private boolean romLoaded;

    public CPU(Decode decoder, Memory memory) {
        this.decoder = decoder;
        this.memory = memory;
        romLoaded = false;
    }

    public void load_rom(File f)throws RuntimeException {
        byte[] fileContent = new byte[0];
        String fileName = f.getName();
        if(!fileName.substring(fileName.lastIndexOf(".")).equals(".ch8")) {
            throw new RuntimeException("Wrong File");
        }
        try {
            fileContent = Files.readAllBytes(f.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Error Reading File");
        }

        for(int i = 0; i < fileContent.length; i++) {
            memory.ram[memory.STARTING_ADDRESS + i] = fileContent[i];
        }
        romLoaded = true;
        System.out.println("Rom Loaded");
    }

    public void cycle() {

            System.out.println("Program Counter is " + memory.PC +" Vf is " + memory.V[0xf] + " DT is "+ Byte.toUnsignedInt(memory.delayTimer));
            byte[] op = new byte[]{memory.ram[Short.toUnsignedInt(memory.PC)],memory.ram[Short.toUnsignedInt(memory.PC)+1]};
            String instruction = Utils.bytesToHex(op).toUpperCase();
            System.out.println("Executing : " + instruction);

            decoder.decode_instruction(instruction);

    }

    public boolean isRomLoaded() {
        return romLoaded;
    }
}

