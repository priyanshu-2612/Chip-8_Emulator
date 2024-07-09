package main.java;

public class Decode {
    private InstructionSet is;

    public Decode(InstructionSet is){
        this.is = is;
    }
    public void decode_instruction(String opcode){
        char c = opcode.charAt(0),
                c1 = opcode.charAt(1),
                    c2 = opcode.charAt(2),
                        c3 = opcode.charAt(3);

        String nnn = opcode.substring(1,4),
                kk = opcode.substring(2,4),
                    x = opcode.substring(1,2),
                        y = opcode.substring(2,3),
                            n = opcode.substring(3,4);
        if(c=='0'){
            if(opcode.equals("00E0")) is.cls();
            else if(opcode.equals("00EE")) is.ret();
            else throw new RuntimeException("Invalid Instruction");
        }
        else if(c=='1'){
            is.jmp(nnn);
        }
        else if(c=='2'){
            is.call(nnn);
        }
        else if(c=='3'){
            System.out.println(kk);
            int k = Integer.parseInt(kk,16);
            is.skipIfEqual(Character.toString(c1),(short) k);
        }
        else if(c=='4'){
            int k = Integer.parseInt(kk,16);
            is.skipIfNotEqual(Character.toString(c1),(short) k);
        }
        else if(c=='5'){
            is.skipIfRegistersAreEqual(Character.toString(c1),Character.toString(c2));
        }
        else if(c=='6'){
            is.load(x, (byte)Integer.parseInt(kk,16));
        }
        else if(c=='7'){
            is.add(x,(byte)Integer.parseInt(kk,16));
        }
        else if(c=='8'){
            if(c3=='0'){
                is.set(x,y);
            }
            else if(c3=='1'){
                is.or(x,y);
            }
            else if(c3=='2'){
                is.and(x,y);
            }
            else if(c3=='3'){
                is.xor(x,y);
            }
            else if(c3=='4'){
                is.add(x,y);
            }
            else if(c3=='5'){
                is.sub(x,y);
            }
            else if(c3=='6'){
                is.shr(x,y);
            }
            else if(c3=='7'){
                is.subn(x,y);
            }
            else if(c3=='E'){
                is.shl(x,y);
            }
            else throw new RuntimeException("Invalid Instruction");
        }
        else if(c=='9'){
            is.sne(x,y);
        }
        else if(c=='A'){
            is.ldi(nnn);
        }
        else if(c=='B'){
            is.jmpv0(nnn);
        }
        else if(c=='C'){
            is.rndvx(x,kk);
        }
        else if(c=='D'){
            System.out.println("Exec drw");
            is.drw(x,y, Integer.parseInt(n,16));
        }
        else if(c=='E'){
            if(kk.equals("9E")) is.skpvx(x);
            else is.sknpvx(x);
        }
        else if(c=='F'){
            switch (kk) {
                case "07" : {
                    is.ldvx(x);
                    break;
                }
                case "0A" : {
                    is.ldvxk(x);
                    break;
                }
                case "15" : {
                    is.lddt(x);
                    break;
                }
                case "18" : {
                    is.ldst(x);
                    break;
                }
                case "1E" : {
                    is.addi(x);
                    break;
                }
                case "29" : {
                    is.ldfvx(x);
                    break;
                }
                case "33" : {
                    is.ldb(x);
                    break;
                }
                case "55" : {
                    is.ldivx(x);
                    break;
                }
                case "65" : {
                    is.ldvxi(x);
                    break;
                }
                default : throw new RuntimeException("Invalid Instruction");
            }
        }
        else throw new RuntimeException("Invalid Instruction");
    }
}
