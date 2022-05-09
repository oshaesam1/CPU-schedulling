
package os_ass3;
import java.util.Scanner;
/**
 *
 * @author Hoda
 */
public  class OS_ASS3 {

   

    
    public static void main(String[] args) {
         Scanner input = new Scanner(System.in);
        System.err.println("Choose Algorithm Scheduling\n 1-Priority Scheduling   2-SJF Scheduling   3-SRT Scheduling "
                + "  4-AGAT Scheduling ");
        int  c = input.nextInt();
        if(c==1){
              Priority priority = new Priority();
            priority.NonPreem(input);
        }else if (c==2){
             SJF sjf= new SJF();
            sjf.buildGanttChart(input);
        }else if (c==3){
            SRT srt=new SRT();
            srt.run();
        }else if (c==4){
             AGAT agat=new AGAT();
             agat.run(input);
        }
    
         

            
           input.close();
        }
    
    

    
    
    
}
    

