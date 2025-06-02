package com.nobroker.streamSphere;
import java.util.Date;
import java.util.Calendar;
import com.nobroker.streamSphere.models.Movies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class StreamSphereApplication {

	public static void main(String[] args) {SpringApplication.run(StreamSphereApplication.class, args);

		Movies poke = new Movies();
		poke.setActorList("Sharuk,Salman,Aamir");
		poke.setDescription("Hindi Movie");
		poke.setRating(4.66F);
		poke.setMovieId(2324432132342L);
		poke.setMovieName("sammy movie");
		poke.setImageType("2143ed33r43d31d134r5");
		poke.setMoviePoster("2143ed33r43d31d134r5");
		poke.setCreatedAt(new Date());
		poke.setUpdatedAt(new Date());
		Calendar cal = Calendar.getInstance();
		cal.set(2026, Calendar.JUNE, 2);
		poke.setReleaseDate(cal.getTime());
		poke.setRunTime("34.334.22");
		poke.setUpdatedBy(234243424352344L);


		System.out.println(poke);


	}
}
