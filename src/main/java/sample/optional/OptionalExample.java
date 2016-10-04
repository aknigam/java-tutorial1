package sample.optional;

import java.util.Optional;

/**
 * Created by a.nigam on 11/05/16.
 */
public class OptionalExample {


    public static void main(String[] args) {
        Computer comp = new Computer();
        Optional<Computer> computer = Optional.ofNullable(null);

        String name = computer.flatMap(Computer::getSoundcard)
                .flatMap(Soundcard::getUSB)
                .map(USB::getVersion)
                .orElse("UNKNOWN version");

        System.out.println(name);

        comp = new Computer("10.0.0.9");
        computer = Optional.ofNullable(null);

        name = computer.flatMap(Computer::getSoundcard)
                .flatMap(Soundcard::getUSB)
                .map(USB::getVersion)
                .orElse("UNKNOWN version");

        System.out.println(name);
    }

}

class Computer{

    public Computer(String version) {
        soundcard = Optional.of(new Soundcard(version));
    }

    public Computer() {

    }

    public Optional<Soundcard> getSoundcard() {
        return soundcard;
    }

    private Optional<Soundcard> soundcard = Optional.empty();
}

class Soundcard{

    public Soundcard(String version) {
        this.usb = Optional.of(new USB(version));
    }

    public Optional<USB> getUSB() {
        return usb;
    }

    private Optional<USB> usb = null;
}

class USB{
    public USB(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    String version;
}
