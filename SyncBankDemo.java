import java.util.Scanner;
import java.io.*;
class SyncBank
{
	synchronized void deposit()
	{
		int amt;
		double balance;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter amount to be deposited in : "+Thread.currentThread());
		amt=sc.nextInt();
		try
		{
			DataInputStream di=new DataInputStream(new FileInputStream("Bank.txt"));
			balance=di.readDouble();
			balance=balance+amt;
			di.close();

			DataOutputStream dout=new DataOutputStream(new FileOutputStream("Bank.txt"));
			dout.writeDouble(balance);
			dout.close();
			System.out.println("Balance after deposit "+balance);			
		}
		catch(Exception e){}
	}
}
class SyncThread extends Thread
{
	SyncBank ac;

	SyncThread(SyncBank b)
	{
		ac=b;
	}
	public void run()
	{
		ac.deposit();
	}
}
class SyncBankDemo
{
	public static void main(String args[]) throws IOException,InterruptedException
	{
		DataOutputStream dout=new DataOutputStream(new FileOutputStream("Bank.txt"));
		dout.writeDouble(1000);
		dout.close();

		SyncBank b=new SyncBank();
		SyncThread t1=new SyncThread(b);
		SyncThread t2=new SyncThread(b);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		DataInputStream di=new DataInputStream(new FileInputStream("Bank.txt"));
		double bal;
		bal=di.readDouble();
		di.close();
		System.out.println("final balance "+bal);
	}
}