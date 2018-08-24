/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SaveEditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Kristian
 */
public class SaveData {
    
    public int selectedItemSlot = 0;
    public String filepath;    
    
    public Boolean immortal = true;
    public int shards = 0;
    public List<Talent> talents;
    public int spentTalents = 0;
    public int maxTalents = 0;
    public int talentPoints = 0;

    public int ability1 = 0;
    public int ability2 = 0;
    public int ability3 = 0;
    public List<Integer> ability;

    public int item5id;
    public int item4id;
    public int item3id;
    public int item2id;
    public int item1id;	

    public int item4c;
    public int item3c;
    public int item2c;
    public int item1c;

    public int pot;
    public int shield;
    public int wep2;
    public int wep1;
    public int boot;
    public int acc;
    public int aura;
    public int helm;

    public List<Item> inv;

    public enum InvSubsection{
        player,
        savepoint,
        talent,
        equipped,
        stats,
        ability,
        inventory,
        itemcollected
    }

    private static byte[] decrypt(String input){
            return Base64.getMimeDecoder().decode(input);
    }

    private static byte[] encrypt(String input){
        byte[] data = Base64.getMimeEncoder().encode(input.getBytes());
        String s = new String(data);
        s = s.replaceAll("(?:\\r\\n|\\n\\r|\\n|\\r)", "");
        return s.getBytes() ;
    }

    public static String loadSave(File dir) throws Exception {
            String str = new String(Files.readAllBytes(dir.toPath()),"UTF-8");
            str = str.replaceAll("[^A-Za-z0-9()=]", "");
            return new String(decrypt(str)).trim();
    }
    
    public static void saveSave(SaveData s) throws FileNotFoundException, IOException{
        String out = "";
        out += "[player]\n";
        out += String.format("immortal=\"%d.000000\"\n", s.immortal ? 1 : 0 );
        out += "[savepoint]\n";
        out += String.format("hope=\"%d.000000\"\n", s.shards );
        out += "[TALENTS]\n";
        for(Talent t : s.talents ){
            out += String.format("%d=\"%d.000000\"\n", t.id, t.enabled ? 1:0);
        }
        out += "[equipped]\n";
        out += String.format("item4number=\"%d.000000\"\n", s.item4c);
        out += String.format("item3number=\"%d.000000\"\n", s.item3c);
        out += String.format("item2number=\"%d.000000\"\n", s.item2c);
        out += String.format("item1number=\"%d.000000\"\n", s.item1c);
        
        out += String.format("item5=\"%d.000000\"\n", s.item5id);
        out += String.format("item4=\"%d.000000\"\n", s.item4id);
        out += String.format("item3=\"%d.000000\"\n", s.item3id);
        out += String.format("item2=\"%d.000000\"\n", s.item2id);
        out += String.format("item1=\"%d.000000\"\n", s.item1id
        );
        out += String.format("pot=\"%d.000000\"\n", s.pot);
        out += String.format("shield=\"%d.000000\"\n", s.shield);
        out += String.format("wep2=\"%d.000000\"\n", s.wep2);
        out += String.format("wep1=\"%d.000000\"\n", s.wep1);
        out += String.format("boot=\"%d.000000\"\n", s.boot);
        out += String.format("acc=\"%d.000000\"\n", s.acc);
        out += String.format("aura=\"%d.000000\"\n", s.aura);
        out += String.format("helm=\"%d.000000\"\n", s.helm);
        out += "[STATS]\n";
        out += String.format("SPENTTALENTPOINTS=\"%d.000000\"\n", s.spentTalents);
        out += String.format("MAXTALENTPOINTS=\"%d.000000\"\n", s.maxTalents);
        out += String.format("TALENTPOINTS=\"%d.000000\"\n", s.talentPoints);
        out += "[ability]\n";
        out += String.format("abilityslot3=\"%d.000000\"\n", s.ability1);
        out += String.format("abilityslot2=\"%d.000000\"\n", s.ability2);
        out += String.format("abilityslot1=\"%d.000000\"\n", s.ability3);
        for(int i = 40; i >= 0; i--){
            out += String.format("slot%d=\"%d.000000\"\n", i, s.ability.get(i));
        }
        out += "[inventory]\n";
        for(Item item:s.inv){
            out += item.output();
        }
        out += "[ITEMCOLLECTED]\n";
        for( int i = 168; i >= 0; i--){
            out += String.format("SLOT%d=\"167-1486-666.20-84\"\n", i);
        }
        out += "COUNT=\"169.000000\"";                
        File f = new File(s.filepath);
        
        //byte[] extraGarbage = "ÚïëàøäñÜèììîñóàë¼¡°­¯¯¯¯¯¯¡ÚòàõäïîèíóÜçîïä¼¡¸¸¸¸­¯¯¯¯¯¯¡ÚÓÀËÄÍÓÒÜ³¸¼¡¯­¯¯¯¯¯¯¡³·¼¡¯­¯¯¯¯¯¯¡³¶¼¡¯­¯¯¯¯¯¯¡³µ¼¡¯­¯¯¯¯¯¯¡³´¼¡¯­¯¯¯¯¯¯¡³³¼¡¯­¯¯¯¯¯¯¡³²¼¡¯­¯¯¯¯¯¯¡³±¼¡¯­¯¯¯¯¯¯¡³°¼¡¯­¯¯¯¯¯¯¡³¯¼¡¯­¯¯¯¯¯¯¡²¸¼¡¯­¯¯¯¯¯¯¡²·¼¡¯­¯¯¯¯¯¯¡²¶¼¡¯­¯¯¯¯¯¯¡²µ¼¡¯­¯¯¯¯¯¯¡²´¼¡¯­¯¯¯¯¯¯¡²³¼¡¯­¯¯¯¯¯¯¡²²¼¡¯­¯¯¯¯¯¯¡²±¼¡¯­¯¯¯¯¯¯¡²°¼¡¯­¯¯¯¯¯¯¡²¯¼¡¯­¯¯¯¯¯¯¡±¸¼¡¯­¯¯¯¯¯¯¡±·¼¡¯­¯¯¯¯¯¯¡±¶¼¡¯­¯¯¯¯¯¯¡±µ¼¡¯­¯¯¯¯¯¯¡±´¼¡¯­¯¯¯¯¯¯¡±³¼¡¯­¯¯¯¯¯¯¡±²¼¡¯­¯¯¯¯¯¯¡±±¼¡¯­¯¯¯¯¯¯¡±°¼¡¯­¯¯¯¯¯¯¡±¯¼¡¯­¯¯¯¯¯¯¡°¸¼¡¯­¯¯¯¯¯¯¡°·¼¡¯­¯¯¯¯¯¯¡°¶¼¡¯­¯¯¯¯¯¯¡°µ¼¡¯­¯¯¯¯¯¯¡°´¼¡¯­¯¯¯¯¯¯¡°³¼¡¯­¯¯¯¯¯¯¡°²¼¡¯­¯¯¯¯¯¯¡°±¼¡¯­¯¯¯¯¯¯¡°°¼¡¯­¯¯¯¯¯¯¡°¯¼¡¯­¯¯¯¯¯¯¡¸¼¡¯­¯¯¯¯¯¯¡·¼¡¯­¯¯¯¯¯¯¡¶¼¡¯­¯¯¯¯¯¯¡µ¼¡¯­¯¯¯¯¯¯¡´¼¡¯­¯¯¯¯¯¯¡³¼¡¯­¯¯¯¯¯¯¡²¼¡¯­¯¯¯¯¯¯¡±¼¡¯­¯¯¯¯¯¯¡°¼¡¯­¯¯¯¯¯¯¡¯¼¡¯­¯¯¯¯¯¯¡ÚäðôèïïäãÜèóäì³íôìáäñ¼¡¯­¯¯¯¯¯¯¡èóäì²íôìáäñ¼¡¯­¯¯¯¯¯¯¡èóäì±íôìáäñ¼¡¯­¯¯¯¯¯¯¡èóäì°íôìáäñ¼¡¯­¯¯¯¯¯¯¡èóäì´¼¡¯­¯¯¯¯¯¯¡èóäì³¼¡¯­¯¯¯¯¯¯¡èóäì²¼¡¯­¯¯¯¯¯¯¡èóäì±¼¡¯­¯¯¯¯¯¯¡èóäì°¼¡¯­¯¯¯¯¯¯¡ïîó¼¡¯­¯¯¯¯¯¯¡òçèäëã¼¡¯­¯¯¯¯¯¯¡öäï±¼¡¯­¯¯¯¯¯¯¡öäï°¼¡¯­¯¯¯¯¯¯¡áîîó¼¡¯­¯¯¯¯¯¯¡àââ¼¡¯­¯¯¯¯¯¯¡àôñà¼¡¯­¯¯¯¯¯¯¡çäëì¼¡¯­¯¯¯¯¯¯¡ÚÒÓÀÓÒÜÒÏÄÍÓÓÀËÄÍÓÏÎÈÍÓÒ¼¡¯­¯¯¯¯¯¯¡ÌÀ×ÓÀËÄÍÓÏÎÈÍÓÒ¼¡¯­¯¯¯¯¯¯¡ÓÀËÄÍÓÏÎÈÍÓÒ¼¡¯­¯¯¯¯¯¯¡ÚàáèëèóøÜàáèëèóøòëîó²¼¡¯­¯¯¯¯¯¯¡àáèëèóøòëîó±¼¡¯­¯¯¯¯¯¯¡àáèëèóøòëîó°¼¡¯­¯¯¯¯¯¯¡òëîó³¯¼¡¯­¯¯¯¯¯¯¡òëîó²¸¼¡¯­¯¯¯¯¯¯¡òëîó²·¼¡¯­¯¯¯¯¯¯¡òëîó²¶¼¡¯­¯¯¯¯¯¯¡òëîó²µ¼¡¯­¯¯¯¯¯¯¡òëîó²´¼¡¯­¯¯¯¯¯¯¡òëîó²³¼¡¯­¯¯¯¯¯¯¡òëîó²²¼¡¯­¯¯¯¯¯¯¡òëîó²±¼¡¯­¯¯¯¯¯¯¡òëîó²°¼¡¯­¯¯¯¯¯¯¡òëîó²¯¼¡¯­¯¯¯¯¯¯¡òëîó±¸¼¡¯­¯¯¯¯¯¯¡òëîó±·¼¡¯­¯¯¯¯¯¯¡òëîó±¶¼¡¯­¯¯¯¯¯¯¡òëîó±µ¼¡¯­¯¯¯¯¯¯¡òëîó±´¼¡¯­¯¯¯¯¯¯¡òëîó±³¼¡¯­¯¯¯¯¯¯¡òëîó±²¼¡¯­¯¯¯¯¯¯¡òëîó±±¼¡¯­¯¯¯¯¯¯¡òëîó±°¼¡¯­¯¯¯¯¯¯¡òëîó±¯¼¡¯­¯¯¯¯¯¯¡òëîó°¸¼¡¯­¯¯¯¯¯¯¡òëîó°·¼¡¯­¯¯¯¯¯¯¡òëîó°¶¼¡¯­¯¯¯¯¯¯¡òëîó°µ¼¡¯­¯¯¯¯¯¯¡òëîó°´¼¡¯­¯¯¯¯¯¯¡òëîó°³¼¡¯­¯¯¯¯¯¯¡òëîó°²¼¡¯­¯¯¯¯¯¯¡òëîó°±¼¡¯­¯¯¯¯¯¯¡òëîó°°¼¡¯­¯¯¯¯¯¯¡òëîó°¯¼¡¯­¯¯¯¯¯¯¡òëîó¸¼¡¯­¯¯¯¯¯¯¡òëîó·¼¡¯­¯¯¯¯¯¯¡òëîó¶¼¡¯­¯¯¯¯¯¯¡òëîóµ¼¡¯­¯¯¯¯¯¯¡òëîó´¼¡¯­¯¯¯¯¯¯¡òëîó³¼¡¯­¯¯¯¯¯¯¡òëîó²¼¡¯­¯¯¯¯¯¯¡òëîó±¼¡¯­¯¯¯¯¯¯¡òëîó°¼¡¯­¯¯¯¯¯¯¡òëîó¯¼¡¯­¯¯¯¯¯¯¡ÚèíõäíóîñøÜòëîó°µ·¼¡¯¹¯¹¡òëîó°µ¶¼¡¯¹¯¹¡òëîó°µµ¼¡¯¹¯¹¡òëîó°µ´¼¡¯¹¯¹¡òëîó°µ³¼¡¯¹¯¹¡òëîó°µ²¼¡¯¹¯¹¡òëîó°µ±¼¡¯¹¯¹¡òëîó°µ°¼¡¯¹¯¹¡òëîó°µ¯¼¡¯¹¯¹¡òëîó°´¸¼¡¯¹¯¹¡òëîó°´·¼¡¯¹¯¹¡òëîó°´¶¼¡¯¹¯¹¡òëîó°´µ¼¡¯¹¯¹¡òëîó°´´¼¡¯¹¯¹¡òëîó°´³¼¡¯¹¯¹¡òëîó°´²¼¡¯¹¯¹¡òëîó°´±¼¡¯¹¯¹¡òëîó°´°¼¡¯¹¯¹¡òëîó°´¯¼¡¯¹¯¹¡òëîó°³¸¼¡¯¹¯¹¡òëîó°³·¼¡¯¹¯¹¡òëîó°³¶¼¡¯¹¯¹¡òëîó°³µ¼¡¯¹¯¹¡òëîó°³´¼¡¯¹¯¹¡òëîó°³³¼¡¯¹¯¹¡òëîó°³²¼¡¯¹¯¹¡òëîó°³±¼¡¯¹¯¹¡òëîó°³°¼¡¯¹¯¹¡òëîó°³¯¼¡¯¹¯¹¡òëîó°²¸¼¡¯¹¯¹¡òëîó°²·¼¡¯¹¯¹¡òëîó°²¶¼¡¯¹¯¹¡òëîó°²µ¼¡¯¹¯¹¡òëîó°²´¼¡¯¹¯¹¡òëîó°²³¼¡¯¹¯¹¡òëîó°²²¼¡¯¹¯¹¡òëîó°²±¼¡¯¹¯¹¡òëîó°²°¼¡¯¹¯¹¡òëîó°²¯¼¡¯¹¯¹¡òëîó°±¸¼¡¯¹¯¹¡òëîó°±·¼¡¯¹¯¹¡òëîó°±¶¼¡¯¹¯¹¡òëîó°±µ¼¡¯¹¯¹¡òëîó°±´¼¡¯¹¯¹¡òëîó°±³¼¡¯¹¯¹¡òëîó°±²¼¡¯¹¯¹¡òëîó°±±¼¡¯¹¯¹¡òëîó°±°¼¡¯¹¯¹¡òëîó°±¯¼¡¯¹¯¹¡òëîó°°¸¼¡¯¹¯¹¡òëîó°°·¼¡¯¹¯¹¡òëîó°°¶¼¡¯¹¯¹¡òëîó°°µ¼¡¯¹¯¹¡òëîó°°´¼¡¯¹¯¹¡òëîó°°³¼¡¯¹¯¹¡òëîó°°²¼¡¯¹¯¹¡òëîó°°±¼¡¯¹¯¹¡òëîó°°°¼¡¯¹¯¹¡òëîó°°¯¼¡¯¹¯¹¡òëîó°¯¸¼¡¯¹¯¹¡òëîó°¯·¼¡¯¹¯¹¡òëîó°¯¶¼¡¯¹¯¹¡òëîó°¯µ¼¡¯¹¯¹¡òëîó°¯´¼¡¯¹¯¹¡òëîó°¯³¼¡¯¹¯¹¡òëîó°¯²¼¡¯¹¯¹¡òëîó°¯±¼¡¯¹¯¹¡òëîó°¯°¼¡¯¹¯¹¡òëîó°¯¯¼¡¯¹¯¹¡òëîó¸¸¼¡¯¹¯¹¡òëîó¸·¼¡¯¹¯¹¡òëîó¸¶¼¡¯¹¯¹¡òëîó¸µ¼¡¯¹¯¹¡òëîó¸´¼¡¯¹¯¹¡òëîó¸³¼¡¯¹¯¹¡òëîó¸²¼¡¯¹¯¹¡òëîó¸±¼¡¯¹¯¹¡òëîó¸°¼¡¯¹¯¹¡òëîó¸¯¼¡¯¹¯¹¡òëîó·¸¼¡¯¹¯¹¡òëîó··¼¡¯¹¯¹¡òëîó·¶¼¡¯¹¯¹¡òëîó·µ¼¡¯¹¯¹¡òëîó·´¼¡¯¹¯¹¡òëîó·³¼¡¯¹¯¹¡òëîó·²¼¡¯¹¯¹¡òëîó·±¼¡¯¹¯¹¡òëîó·°¼¡¯¹¯¹¡òëîó·¯¼¡¯¹¯¹¡òëîó¶¸¼¡¯¹¯¹¡òëîó¶·¼¡¯¹¯¹¡òëîó¶¶¼¡¯¹¯¹¡òëîó¶µ¼¡¯¹¯¹¡òëîó¶´¼¡¯¹¯¹¡òëîó¶³¼¡¯¹¯¹¡òëîó¶²¼¡¯¹¯¹¡òëîó¶±¼¡¯¹¯¹¡òëîó¶°¼¡¯¹¯¹¡òëîó¶¯¼¡¯¹¯¹¡òëîóµ¸¼¡¯¹¯¹¡òëîóµ·¼¡¯¹¯¹¡òëîóµ¶¼¡¯¹¯¹¡òëîóµµ¼¡¯¹¯¹¡òëîóµ´¼¡¯¹¯¹¡òëîóµ³¼¡¯¹¯¹¡òëîóµ²¼¡¯¹¯¹¡òëîóµ±¼¡¯¹¯¹¡òëîóµ°¼¡¯¹¯¹¡òëîóµ¯¼¡¯¹¯¹¡òëîó´¸¼¡¯¹¯¹¡òëîó´·¼¡¯¹¯¹¡òëîó´¶¼¡¯¹¯¹¡òëîó´µ¼¡¯¹¯¹¡òëîó´´¼¡¯¹¯¹¡òëîó´³¼¡¯¹¯¹¡òëîó´²¼¡¯¹¯¹¡òëîó´±¼¡¯¹¯¹¡òëîó´°¼¡¯¹¯¹¡òëîó´¯¼¡¯¹¯¹¡òëîó³¸¼¡¯¹¯¹¡òëîó³·¼¡¯¹¯¹¡òëîó³¶¼¡¯¹¯¹¡òëîó³µ¼¡¯¹¯¹¡òëîó³´¼¡¯¹¯¹¡òëîó³³¼¡¯¹¯¹¡òëîó³²¼¡¯¹¯¹¡òëîó³±¼¡¯¹¯¹¡òëîó³°¼¡¯¹¯¹¡òëîó³¯¼¡¯¹¯¹¡òëîó²¸¼¡¯¹¯¹¡òëîó²·¼¡¯¹¯¹¡òëîó²¶¼¡¯¹¯¹¡òëîó²µ¼¡¯¹¯¹¡òëîó²´¼¡¯¹¯¹¡òëîó²³¼¡¯¹¯¹¡òëîó²²¼¡¯¹¯¹¡òëîó²±¼¡¯¹¯¹¡òëîó²°¼¡¯¹¯¹¡òëîó²¯¼¡¯¹¯¹¡òëîó±¸¼¡¯¹¯¹¡òëîó±·¼¡¯¹¯¹¡òëîó±¶¼¡¯¹¯¹¡òëîó±µ¼¡¯¹¯¹¡òëîó±´¼¡¯¹¯¹¡òëîó±³¼¡¯¹¯¹¡òëîó±²¼¡¯¹¯¹¡òëîó±±¼¡¯¹¯¹¡òëîó±°¼¡¯¹¯¹¡òëîó±¯¼¡¯¹¯¹¡òëîó°¸¼¡¯¹¯¹¡òëîó°·¼¡¯¹¯¹¡òëîó°¶¼¡¯¹¯¹¡òëîó°µ¼¡¯¹¯¹¡òëîó°´¼¡¯¹¯¹¡òëîó°³¼¡¯¹¯¹¡òëîó°²¼¡¯¹¯¹¡òëîó°±¼¡¯¹¯¹¡òëîó°°¼¡¯¹¯¹¡òëîó°¯¼¡¯¹¯¹¡òëîó¸¼¡¯¹¯¹¡òëîó·¼¡¯¹¯¹¡òëîó¶¼¡¯¹¯¹¡òëîóµ¼¡¯¹¯¹¡òëîó´¼¡¯¹¯¹¡òëîó³¼¡¯¹¯¹¡òëîó²¼¡¯¹¯¹¡òëîó±¼¡¯¹¯¹¡òëîó°¼¡¯¹¯¹¡òëîó¯¼¡¯¹¯¹¡ÚÈÓÄÌÂÎËËÄÂÓÄÃÜÂÎÔÍÓ¼¡¯­¯¯¯¯¯¯¡".getBytes();
        
        FileOutputStream stream = new FileOutputStream(s.filepath);
        try{
            byte[] toWrite = new byte[32000];
            byte[] data = encrypt(out);
            System.arraycopy(data, 0, toWrite, 0, data.length );
            //System.arraycopy(extraGarbage, 0, toWrite, data.length, extraGarbage.length );
            
            stream.write( toWrite );
        } finally {
            stream.close();
        }
       
    }

    public SaveData(String path) throws Exception{
        filepath = path;
        
        File f = new File(path);
        String data = loadSave(f);

        InvSubsection invsub = InvSubsection.player;

        talents = new ArrayList<Talent>();
        ability = new ArrayList<Integer>();
        inv = new ArrayList<Item>();
        int i = 0;

        Scanner scanner = new Scanner(data);

        while (scanner.hasNextLine()){
                String line = scanner.nextLine();

                switch(line){
                case "[player]":invsub = InvSubsection.player; break;
                case "[savepoint]":invsub = InvSubsection.savepoint; break;
                case "[TALENTS]":invsub = InvSubsection.talent; i = 0;break;
                case "[equipped]":invsub = InvSubsection.equipped;
                break;
                case "[STATS]":invsub = InvSubsection.stats; break;
                case "[ability]":invsub = InvSubsection.ability; 
                        ability1 = lineToInt(scanner.nextLine());
                        ability2 = lineToInt(scanner.nextLine());
                        ability3 = lineToInt(scanner.nextLine());
                break;
                case "[inventory]":invsub = InvSubsection.inventory; break;
                case "[ITEMCOLLECTED]":invsub = InvSubsection.itemcollected; break;
                default:
                        switch(invsub){
                        case player:
                                immortal = lineToBool(line);
                                break;
                        case savepoint:
                                shards = lineToInt(line);
                                break;
                        case talent:
                                talents.add(new Talent(49-i, lineToBool(line)));
                                i++;
                                break;
                        case equipped:
                                //TODO
                                item4c = lineToInt(line);				
                                item3c = lineToInt(scanner.nextLine());
                                item2c = lineToInt(scanner.nextLine());
                                item1c = lineToInt(scanner.nextLine());

                                item5id = lineToInt(scanner.nextLine());
                                item4id = lineToInt(scanner.nextLine());
                                item3id = lineToInt(scanner.nextLine());
                                item2id = lineToInt(scanner.nextLine());
                                item1id = lineToInt(scanner.nextLine());

                                pot = lineToInt(scanner.nextLine());
                                shield = lineToInt(scanner.nextLine());
                                wep2 = lineToInt(scanner.nextLine());
                                wep1 = lineToInt(scanner.nextLine());
                                boot = lineToInt(scanner.nextLine());
                                acc = lineToInt(scanner.nextLine());

                                aura = lineToInt(scanner.nextLine());
                                helm = lineToInt(scanner.nextLine());

                                break;
                        case stats:
                                spentTalents = lineToInt(line);
                                maxTalents = lineToInt(scanner.nextLine());
                                talentPoints = lineToInt(scanner.nextLine());
                                break;
                        case ability:
                                ability.add(lineToInt(line));
                                break;
                        case inventory:
                                inv.add( new Item(line) );
                                break;
                        case itemcollected:
                        }
                }			
        }
        scanner.close();
    }

    public static Boolean lineToBool(String l){
        return l.split("=")[1].charAt(1) == '1';
    }

    public static int lineToInt(String l){
        String s = l.split("=")[1];
        s = s.substring(1, s.length()-1);
        return (int)Double.parseDouble(s);
    }
	
    
}
