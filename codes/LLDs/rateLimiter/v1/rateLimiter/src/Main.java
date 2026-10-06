import dao.UserData;
import entity.RateLimiter;
import entity.User;
import enums.UserType;
import service.RateLimiterService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        User rohan = new User(1L,"rohan", UserType.PremiumUser);
        User rahul = new User(2L, "rahul", UserType.FreeUser);
        UserData userData = new UserData();
        userData.register(rahul);
        userData.register(rohan);

        RateLimiterService service = new RateLimiterService(userData);

        for(int i=0; i<10; i++){
            System.out.println("Request "+i+", user: "+rohan.getName()+"\t "+service.allow(rohan.getId()));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for(int i=0; i<10; i++){
            System.out.println("Request "+i+", user: "+rahul.getName()+"\t "+service.allow(rahul.getId()));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}