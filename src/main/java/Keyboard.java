package main.java;

import javafx.scene.input.KeyCode;

public class Keyboard {
    int[] key = new int[16];
    public void setKeyPressed(KeyCode k){
        switch (k) {
            case DIGIT1:
                key[0] = 1;
                break;
            case DIGIT2:
                key[1] = 1;
                break;
            case DIGIT3:
                key[2] = 1;
                break;
            case DIGIT4:
                key[3] = 1;
                break;
            case Q:
                key[4] = 1;
                break;
            case W:
                key[5] = 1;
                break;
            case E:
                key[6] = 1;
                break;
            case R:
                key[7] = 1;
                break;
            case A:
                key[8] = 1;
                break;
            case S:
                key[9] = 1;
                break;
            case D:
                key[10] = 1;
                break;
            case F:
                key[11] = 1;
                break;
            case Z:
                key[12] = 1;
                break;
            case X:
                key[13] = 1;
                break;
            case C:
                key[14] = 1;
                break;
            case V:
                key[15] = 1;
                break;
            default:
                break;
        }
    }

        public void setKeyReleased(KeyCode k){
            switch (k) {
                case DIGIT1:
                    key[0] = 0;
                    break;
                case DIGIT2:
                    key[1] = 0;
                    break;
                case DIGIT3:
                    key[2] = 0;
                    break;
                case DIGIT4:
                    key[3] = 0;
                    break;
                case Q:
                    key[4] = 0;
                    break;
                case W:
                    key[5] = 0;
                    break;
                case E:
                    key[6] = 0;
                    break;
                case R:
                    key[7] = 0;
                    break;
                case A:
                    key[8] = 0;
                    break;
                case S:
                    key[9] = 0;
                    break;
                case D:
                    key[10] = 0;
                    break;
                case F:
                    key[11] = 0;
                    break;
                case Z:
                    key[12] = 0;
                    break;
                case X:
                    key[13] = 0;
                    break;
                case C:
                    key[14] = 0;
                    break;
                case V:
                    key[15] = 0;
                    break;
                default:
                    break;
            }
        }
    }

