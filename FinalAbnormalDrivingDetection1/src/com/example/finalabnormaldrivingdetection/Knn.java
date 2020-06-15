package com.example.finalabnormaldrivingdetection;
import java.io.FileReader;
import java.util.*;

public class Knn {
	public static void main(String args[])
	{ Scanner sc=new Scanner(System.in);
	int n,x1_qi,x2_qi;

	n=sc.nextInt();

	int x1[] = new int[n];
	int x2[] = new int[n];
	String clasi[] = new String[n];
	String In[] = new String[n];
	int d[] = new int[n];
	int c[] = new int[n];
	int r[] = new int[n];

	System.out.println("Enter Acid Durability(x1) , Strength(x2) & Classification (good / bad) : ");
	for(int i=0;i<n;i++)
	{ x1[i] = sc.nextInt();
	x2[i] = sc.nextInt();
	clasi[i] = sc.next();
	}
	System.out.print("Enter Acid Durability(x1) & Strength(x2) of query instance : ");
	x1_qi=sc.nextInt();
	x2_qi=sc.nextInt();

	System.out.print("Enter k = ");
	int k=sc.nextInt();

	for(int i=0;i<n;i++)
	{ d[i]=((x1[i]-x1_qi)*(x1[i]-x1_qi))+((x2[i]-x2_qi)*(x2[i]-x2_qi)); }
	for(int i=0;i<n;i++)
	{ c[i]=d[i]; }

	for(int i=0;i<n;i++)
	{ for(int j=1;j<(n-i);j++)
	{ if(d[j-1]>d[j])
	{int t = d[j-1];
	d[j-1] = d[j];
	d[j] = t;
	}
	}
	}

	for(int i=0;i<n;i++)
	{ for(int j=0;j<n;j++)
	{ if(c[i]==d[j])
	{r[i] = j+1;continue;}
	}
	}
	int g=0,b=0;
	for(int i=0;i<n;i++)
	{ if(r[i] < (k+1))
	{ In[i]="yes";
	if(clasi[i].equals("good")) g++;
	else b++;
	}
	else In[i]="no";
	}

	System.out.println("X1\tX2\tSquare Dist\tRank\tIncluded in KNN\tCategory");
	for(int i=0;i<n;i++)
	{ System.out.println(x1[i]+"\t"+x2[i]+"\t\t"+c[i]+"\t"+r[i]+"\t"+In[i]+"\t\t"+clasi[i]); }

	if(g > b)
	System.out.print("Prediction Banormality");
	else
	System.out.print("Prediction Normal");
	}
	}