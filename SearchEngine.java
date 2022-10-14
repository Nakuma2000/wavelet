import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler 
{
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> word = new ArrayList<String>();
    String s1 = "Default";
    
    public String handleRequest(URI url) 
    {
        String list = "Words: \n";
        if (url.getPath().equals("/")) 
        {
            for(int i = 0; i < word.size(); i++)
            {
                String curr = word.get(i);
                list += "\n" + curr;
            }
            return list;
        } 
        else 
        {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) 
            {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) 
                {
                    word.add(parameters[1]);
                    return String.format("Word added!"); //use stringformat
                }
            }
            else 
            {
                System.out.println("Path: " + url.getPath());
                if (url.getPath().contains("/search")) 
                {
                    String searchResult = "";
                    String[] para = url.getQuery().split("=");
                    if (para[0].equals("s")) 
                    {
                        String searchInput = para[1];
                        for (int i = 0; i < word.size(); i++)
                        {
                            if (word.get(i).contains(searchInput))
                            {
                                System.out.println(word.get(i));
                                searchResult += String.format(word.get(i) + "\n");
                            }
                        }
                    }
                    return String.format(searchResult +"\nSearch Complete!");
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException 
    {
        if(args.length == 0)
        {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}