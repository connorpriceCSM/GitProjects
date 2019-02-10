
public class Burner {

	public enum Temperature
	{

		BLAZING, HOT, WARM, COLD;
	}


	private Temperature myTemperature;
	public Setting mySetting;
	private int timer;
	public final static int TIME_DURATION = 2;

	public Burner()
	{
		myTemperature = Temperature.COLD;
		mySetting =  Setting.OFF;
		timer  = 0;
	}


	public Temperature getMyTemperature() {
		return myTemperature;
	}

	public Setting getMySetting() {
		return mySetting;
	}


	public int getTimer() {
		return timer;
	}


	// Break statements prevent enum from automatically going to HIGH setting when plusButton is called
	// WAS HUGE ISSUE
	// timer = TIME_DURATION accounts for any call of plusButton() or minusButton() not just the stove examples
	public void plusButton()
	{
		switch(mySetting)
		{
		case OFF:
			mySetting = Setting.LOW;
			timer = TIME_DURATION;
			break;
		case LOW:
			mySetting  = Setting.MEDIUM;
			timer = TIME_DURATION;
			break;
		case MEDIUM:
			mySetting  = Setting.HIGH;
			timer = TIME_DURATION;
			break;
		case HIGH:
			//System.out.println("This burner is already at its highest setting!");
		}
	}

	public void minusButton()
	{
		switch(mySetting)
		{
		case OFF:
			//System.out.println("This burner is already turned off!");
		case LOW:
			mySetting  = Setting.OFF;
			timer = TIME_DURATION;
			break;
		case MEDIUM:
			mySetting  = Setting.LOW;
			timer = TIME_DURATION;
			break;
		case HIGH:
			mySetting = Setting.MEDIUM;
			timer = TIME_DURATION;
			break;
		}
	}
	//Setting  Temp
	// OFF --> COLD
	// LOW --> WARM
	// MEDIUM --> HOT
	// HIGH --> BLAZING
	//Break statements big here to prevent more than 1 temperature case being called
	public void updateTemperature()
	{
		if(timer > 0)
		{
			timer--;
			if(timer == 0 )
			{
				switch(myTemperature)
				{
				case COLD:

					if(mySetting.equals(Setting.LOW))
					{
						myTemperature = Temperature.WARM;
					}
					if(mySetting.equals(Setting.MEDIUM) || mySetting.equals(Setting.HIGH))
					{
						myTemperature = Temperature.WARM;
						timer = TIME_DURATION;
					}
					break;

				case WARM:
					if(mySetting.equals(Setting.MEDIUM))
					{
						myTemperature = Temperature.HOT;
					}
					if(mySetting.equals(Setting.HIGH))
					{
						myTemperature = Temperature.HOT;
						timer = TIME_DURATION;
					}
					if(mySetting.equals(Setting.OFF))
					{
						myTemperature = Temperature.COLD;


					}
					break;
				case HOT:
					if(mySetting.equals(Setting.HIGH))
					{
						myTemperature = Temperature.BLAZING;
					}
					if(mySetting.equals(Setting.LOW))
					{
						myTemperature = Temperature.WARM;

					}
					if(mySetting.equals(Setting.OFF))
					{
						myTemperature = Temperature.WARM;
						timer = TIME_DURATION;

					}
					break;
				case BLAZING:
					if(mySetting.equals(Setting.MEDIUM))
					{
						myTemperature = Temperature.HOT;
					}
					if(mySetting.equals(Setting.LOW) || mySetting.equals(Setting.OFF))
					{
						{
							myTemperature = Temperature.HOT;
							timer = TIME_DURATION;
						}
					}
					break;
				}
			}
		}
	}

	//break statement needed here again  to prevent complete spam of changes
	public void display() {

		switch(myTemperature)
		{
		case COLD:
			System.out.println("[" + mySetting.toString() +"]" + ".....cooool");
			break;
		case WARM:
			System.out.println("[" + mySetting.toString() +"]" + ".....warm");
			break;
		case HOT:
			System.out.println("[" + mySetting.toString() +"]" + ".....CAREFUL");
			break;
		case BLAZING:
			System.out.println("[" + mySetting.toString() +"]" + ".....VERY HOT! DON'T TOUCH");
			break;






		}
	}
}


