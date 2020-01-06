//Elif Çal??kan 2016400183 elif.caliskan330@gmail.com
//CMPE436 -Assignment3

bool s = 1;
bit y[2]; 
active proctype P(bit i) {
	do
 	::
		// noncritical section
		atomic {
			y[i] = 1;
			s = i;
		}

		// wait
		(y[1-i] == 0 || s != i);

		//critical section
		y[i]=0;
	
 	od
}

init {
	run P(0);
	run P(1);
}

// Q1.a Mutual Exclusion (no two processes can enter critical section at the same time)
ltl mutex {[]!((y[0]==1 && (y[1]==0 || s!=0)) && (y[1]==1 && (y[0]==0 || s!=1)))}

// Q1.b Absence of unbounded overtaking (absence of individual starvation, fairness)
ltl unbounded1  {[]((y[0]==1 ) -> <>(y[0]==0))}
ltl unbounded2  {[]((y[1]==1 ) -> <>(y[1]==0))}

//Q1.c Occupy critical section infinitely often (liveness)
ltl inf1  {[]<>(y[0]==1)}
ltl inf2  {[]<>(y[1]==1)}