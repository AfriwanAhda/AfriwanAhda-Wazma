//      بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ

package motion.studio.wazma.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Calculates Islamic prayer times based on location
 * Using simplified calculation method
 */
public class PrayerTimesCalculator {
    
    public static Map<String, String> calculate(double latitude, double longitude) {
        Map<String, String> times = new HashMap<>();
        
        Calendar cal = Calendar.getInstance();
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        
        // Simplified calculation for demonstration
        // In production, use a proper library like PrayTimes or Adhan
        
        double timezone = longitude / 15.0;
        double equation = 0.0053 * Math.sin(Math.toRadians(dayOfYear * 360.0 / 365.0));
        
        // Fajr (before sunrise)
        double fajrTime = 5.0 + timezone + equation - (latitude / 15.0);
        
        // Dhuhr (midday)
        double dhuhrTime = 12.0 + timezone + equation;
        
        // Asr (afternoon)
        double asrTime = 15.0 + timezone + equation + (latitude / 30.0);
        
        // Maghrib (sunset)
        double maghribTime = 18.0 + timezone + equation + (latitude / 15.0);
        
        // Isha (night)
        double ishaTime = 19.5 + timezone + equation + (latitude / 15.0);
        
        times.put("Fajr", formatTime(fajrTime));
        times.put("Dhuhr", formatTime(dhuhrTime));
        times.put("Asr", formatTime(asrTime));
        times.put("Maghrib", formatTime(maghribTime));
        times.put("Isha", formatTime(ishaTime));
        
        return times;
    }
    
    private static String formatTime(double time) {
        int hours = (int) time;
        int minutes = (int) ((time - hours) * 60);
        
        // Ensure valid time
        hours = hours % 24;
        if (hours < 0) hours += 24;
        minutes = Math.max(0, Math.min(59, minutes));
        
        return String.format(Locale.US, "%02d:%02d", hours, minutes);
    }
}
