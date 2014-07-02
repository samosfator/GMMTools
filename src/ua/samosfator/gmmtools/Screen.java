package ua.samosfator.gmmtools;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;

import java.awt.*;

import static ua.samosfator.gmmtools.Screen.Kernel32.*;
import static ua.samosfator.gmmtools.Screen.PSAPI.GetModuleBaseNameW;
import static ua.samosfator.gmmtools.Screen.User32DLL.*;

public class Screen {
    private static final int MAX_TITLE_LENGTH = 1024;

    private static final Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

    public static int getWidth() {
        return screen.width;
    }
    public static int getHeight() {
        return screen.height;
    }


    public static String getTitle() {
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];

        GetWindowTextW(GetForegroundWindow(), buffer, MAX_TITLE_LENGTH);
        System.out.println("Active window title: " + Native.toString(buffer));

        return Native.toString(buffer);
    }
    public static String getProcess() {
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        GetWindowTextW(GetForegroundWindow(), buffer, MAX_TITLE_LENGTH);
        PointerByReference pointer = new PointerByReference();
        GetWindowThreadProcessId(GetForegroundWindow(), pointer);
        Pointer process = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, false, pointer.getValue());

        GetModuleBaseNameW(process, null, buffer, MAX_TITLE_LENGTH);
        System.out.println("Active window process: " + Native.toString(buffer));

        return Native.toString(buffer);
    }

    static class PSAPI {
        static {
            Native.register("psapi");
        }

        public static native int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size);
    }

    static class Kernel32 {
        static {
            Native.register("kernel32");
        }

        public static final int PROCESS_QUERY_INFORMATION = 0x0400;
        public static final int PROCESS_VM_READ = 0x0010;

        public static native int GetLastError();

        public static native Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
    }

    static class User32DLL {
        static {
            Native.register("user32");
        }

        public static native int GetWindowThreadProcessId(HWND hWnd, PointerByReference pref);

        public static native HWND GetForegroundWindow();

        public static native int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);
    }
}