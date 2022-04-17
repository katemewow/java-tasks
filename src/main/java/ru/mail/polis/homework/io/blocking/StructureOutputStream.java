package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.stream.IntStream;

/**
 * Вам нужно реализовать StructureOutputStream, который умеет писать данные в файл.
 * Писать поля нужно ручками, с помощью массива байт и методов {@link #write(int)}, {@link #write(byte[])} и так далее
 * 3 тугрика
 */
public class StructureOutputStream extends FileOutputStream {

    public StructureOutputStream(File name) throws FileNotFoundException {
        super(name);
    }

    /**
     * Метод должен вернуть записать прочитанную структуру.
     */
    public void write(Structure structure) throws IOException {
        writeLong(structure.getId());
        writeString(structure.getName());
        writeSubStructures(structure.getSubStructures());
        writeFloat(structure.getCoeff());
        writeFlags(new boolean[]{structure.isFlag1(), structure.isFlag2(), structure.isFlag3(), structure.isFlag4()});
        write(structure.getParam());
        flush();
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        for (Structure structure : structures) {
            write(structure);
        }
    }

    private void writeBoolean(boolean value) throws IOException {
        write(value ? 1 : 0);
    }

    private void writeInt(int value) throws IOException {
        write(ByteBuffer.allocate(Integer.BYTES).putInt(value).array());
    }

    private void writeLong(long value) throws IOException {
        write(ByteBuffer.allocate(Long.BYTES).putLong(value).array());
    }

    private void writeFloat(float value) throws IOException {
        write(ByteBuffer.allocate(Float.BYTES).putFloat(value).array());
    }

    private void writeDouble(double value) throws IOException {
        write(ByteBuffer.allocate(Double.BYTES).putDouble(value).array());
    }

    private void writeString(String str) throws IOException {
        if (str == null) {
            writeInt(-1);
            return;
        }
        writeInt(str.length());
        write(str.getBytes());
    }

    private void writeFlags(boolean[] flags) throws IOException {
        write((byte) IntStream.range(0, flags.length).map(i -> flags[i] ? 1 << i : 0).sum());
    }

    private void writeSubStructure(SubStructure subStructure) throws IOException {
        writeInt(subStructure.getId());
        writeString(subStructure.getName());
        writeBoolean(subStructure.isFlag());
        writeDouble(subStructure.getScore());
    }

    private void writeSubStructures(SubStructure[] subStructures) throws IOException {
        if (subStructures == null) {
            writeInt(-1);
            return;
        }
        writeInt(subStructures.length);
        for (SubStructure subStructure : subStructures) {
            writeSubStructure(subStructure);
        }
    }
}
