package Tests;

import java.util.HashMap;
import java.util.Map;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * @author sumeetmisri@gmail.com
 *
 * This class is an implementation of IRetryAnalyzer used to retry tests.
 * <p />
 * Use it by adding something like this to your @Test annotation: 
 * <pre>
 * @Test(retryAnalyzer = com.mypackage.testng.Retry.class)
 * </pre>
 */
public class retry implements IRetryAnalyzer
{
    private int maxRetries = 1;
    private int sleepBetweenRetries = 2000;
    private final Map<String, Integer> retryMap;

    /**
     * Retry constructor
     */
    public retry()
    {
        retryMap = new HashMap<String, Integer>();
    }

    /**
     * Implemtation of TestNG's retry method
     * 
     * @param result
     *            the ITestResult to retry
     */
    @Override
    public boolean retry(final ITestResult result)
    {
        boolean reRunTest = false;

        setMaxRetriesForTest(result);
        setSleepBetweenRetriesForTest(result);

        final String currentTest = result.getTestContext().getCurrentXmlTest().getName();
        final int currentTry = retryMap.get(currentTest) == null?0:(retryMap.get(currentTest) + 1);
        retryMap.put(currentTest, currentTry);

        System.out.println("currentTest: " + currentTest);

        if (currentTry < maxRetries)
        {
            System.out.println("maxRetries: " + maxRetries);
            System.out.println("currentTry: " + currentTry);
            try
            {
                Thread.sleep(sleepBetweenRetries);
            }
            catch (final InterruptedException e)
            {
                System.out.println(e);
            }
            reRunTest = true;
        }
        else
        {
            System.out.println("Exhausted retry attempts for test: "
                + currentTest);
        }

        // if this method returns true, it will rerun the test once again.
        return reRunTest;
    }

    private void setMaxRetriesForTest(final ITestResult result)
    {
        // Getting the max retries from suite.
        final String maxRetriesStr =
            result
                .getTestContext()
                .getCurrentXmlTest()
                .getParameter("maxRetries");
        if (maxRetriesStr != null)
        {
            try
            {
                maxRetries = Integer.parseInt(maxRetriesStr);
                System.out.println(
                    "maxRetries: " + maxRetries);
            }
            catch (final NumberFormatException e)
            {
                System.out.println(
                    "NumberFormatException while parsing maxRetries from suite file."
                        + e);
            }
            catch (final Exception e)
            {
                System.out.println(
                    "Exception while parsing maxRetries from suite file." + e);
            }
        }
    }

    private void setSleepBetweenRetriesForTest(final ITestResult result)
    {
        // Getting the sleep between retries from suite.
        final String sleepBetweenRetriesStr =
            result
                .getTestContext()
                .getCurrentXmlTest()
                .getParameter("sleepBetweenRetries");
        if (sleepBetweenRetriesStr != null)
        {
            try
            {
                sleepBetweenRetries = Integer.parseInt(sleepBetweenRetriesStr);
                System.out.println("sleepBetweenRetries: "
                    + sleepBetweenRetries);
            }
            catch (final NumberFormatException e)
            {
                System.out.println(
                    "NumberFormatException while parsing sleepBetweenRetries from suite file."
                        + e);
            }
            catch (final Exception e)
            {
                System.out.println(
                    "Exception while parsing sleepBetweenRetries from suite file."
                        + e);
            }
        }
    }
}