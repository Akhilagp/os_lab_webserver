#include<stdio.h>
#include<sys/types.h>
#include<fcntl.h>
#include<stdlib.h>
#include<unistd.h>
int main(int argc,char* argv[])
{
	int i;
	int fd1,fd3;
	char buff[2];
	fd1=open(argv[1],O_RDONLY,0777);
	fd3=creat(argv[2],0777);
	while(read(fd1,buff,1))
	{
		write(fd3,buff,1);
	}
	close(fd1);
	remove(argv[1]);
	close(fd3);
	return 0;
}

