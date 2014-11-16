package me.wener.telletsj.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Come from <a href="http://stackoverflow.com/questions/1268174/phps-strtotime-in-java">here</a>
 */
public final class strtotime
{

    private static final List<Matcher> matchers;

    static
    {
        matchers = new LinkedList<Matcher>();
        matchers.add(new NowMatcher());
        matchers.add(new TomorrowMatcher());
        matchers.add(new DateFormatMatcher(new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z")));
        matchers.add(new DateFormatMatcher(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z")));
        matchers.add(new DateFormatMatcher(new SimpleDateFormat("yyyy-MM-dd")));
        matchers.add(new DateFormatMatcher(new SimpleDateFormat("yyyy MM dd")));
        matchers.add(new DateFormatMatcher(new SimpleDateFormat("yyyy/MM/dd")));
        matchers.add(new DateFormatMatcher(new SimpleDateFormat("yyyy-MM-dd HH:mm")));
        matchers.add(new DateFormatMatcher(new SimpleDateFormat("yyyy/MM/dd HH:mm")));
        // add as many format as you want
    }

    private strtotime()
    {
        throw new UnsupportedOperationException();
    }

    // not thread-safe
    public static void registerMatcher(Matcher matcher)
    {
        matchers.add(matcher);
    }

    /**
     * 同 PHP 中的strtotime
     */
    public static Date strtotime(String input)
    {
        for (Matcher matcher : matchers)
        {
            Date date = matcher.tryConvert(input);

            if (date != null)
            {
                return date;
            }
        }

        return null;
    }

    public static interface Matcher
    {

        public Date tryConvert(String input);
    }

    private static class DateFormatMatcher implements Matcher
    {

        private final DateFormat dateFormat;

        public DateFormatMatcher(DateFormat dateFormat)
        {
            this.dateFormat = dateFormat;
        }

        public Date tryConvert(String input)
        {
            try
            {
                return dateFormat.parse(input);
            } catch (ParseException ex)
            {
                return null;
            }
        }
    }

    private static class NowMatcher implements Matcher
    {

        private final Pattern now = Pattern.compile("now");

        public Date tryConvert(String input)
        {
            if (now.matcher(input).matches())
            {
                return new Date();
            } else
            {
                return null;
            }
        }
    }

    private static class TomorrowMatcher implements Matcher
    {

        private final Pattern tomorrow = Pattern.compile("tomorrow");

        public Date tryConvert(String input)
        {
            if (tomorrow.matcher(input).matches())
            {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, +1);
                return calendar.getTime();
            } else
            {
                return null;
            }
        }
    }
}
