/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188_2013;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Erin King
 * modifications by David Rush 2012 Mar 12
 */
public class CorpsLog {
    private static final boolean SMARTDASHBOARD = true, ENABLE_CONSOLE = true;
    public static void log(String title, String message, boolean dashboard, boolean console) {
        if(console && ENABLE_CONSOLE) System.out.println(title+": "+message);
        if(dashboard && SMARTDASHBOARD) SmartDashboard.putString(title, message);
    }
    public static void log(String title, double value, boolean dashboard, boolean console) {
        if(console && ENABLE_CONSOLE) System.out.println(title+": "+value);
        if(dashboard && SMARTDASHBOARD) SmartDashboard.putDouble(title, value);
    }
    public static void log(String title, int value, boolean dashboard, boolean console) {
        if(console && ENABLE_CONSOLE) System.out.println(title+": "+value);
        if(dashboard && SMARTDASHBOARD) SmartDashboard.putInt(title, value);
    }
    public static void log(String title, boolean value, boolean dashboard, boolean console) {
        if(console && ENABLE_CONSOLE) System.out.println(title+": "+value);
        if(dashboard && SMARTDASHBOARD) SmartDashboard.putBoolean(title, value);
    }
}
