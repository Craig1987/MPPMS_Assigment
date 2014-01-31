/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import javax.media.Time;

/**
 *
 * @author Ryan
 */
public class Annotation {
    String text;
    Time time;

    public Annotation(String txt, Time tme) {
        text = txt;
        time = tme;
    }

    public String getText() {
        return text;
    }

    public Time getTime() {
        return time;
    }
}
