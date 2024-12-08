import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class subnet {
    public static void main(String[] args) throws IOException {
        // Input IP Address
        System.out.println("Enter IP Address:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String ip = br.readLine();
        
        // Split the IP address into octets
        String[] ipParts = ip.split("\\.");
        if (ipParts.length != 4) {
            System.out.println("Invalid IP address format.");
            return;
        }

        int firstOctet = Integer.parseInt(ipParts[0]);
        String mask = null;
        String ipClass = null;

        // Determine IP class and assign subnet mask
        if (firstOctet >= 1 && firstOctet <= 126) {
            ipClass = "Class A";
            mask = "255.0.0.0";
        } else if (firstOctet >= 128 && firstOctet <= 191) {
            ipClass = "Class B";
            mask = "255.255.0.0";
        } else if (firstOctet >= 192 && firstOctet <= 223) {
            ipClass = "Class C";
            mask = "255.255.255.0";
        } else if (firstOctet >= 224 && firstOctet <= 239) {
            ipClass = "Class D (Multicast)";
            mask = "Not applicable";
        } else if (firstOctet >= 240 && firstOctet <= 255) {
            ipClass = "Class E (Experimental)";
            mask = "Not applicable";
        } else {
            System.out.println("Invalid IP address.");
            return;
        }

        System.out.println("IP Address belongs to: " + ipClass);
        System.out.println("Subnet Mask: " + mask);

        // If the subnet mask is valid, calculate network and broadcast addresses
        if (mask != null && !mask.equals("Not applicable")) {
            String networkAddress = calculateNetworkAddress(ipParts, mask.split("\\."));
            String broadcastAddress = calculateBroadcastAddress(networkAddress.split("\\."), mask.split("\\."));
            
            System.out.println("First IP of the block (Network Address): " + networkAddress);
            System.out.println("Last IP of the block (Broadcast Address): " + broadcastAddress);
        }
    }

    // Function to calculate the network address
    private static String calculateNetworkAddress(String[] ipParts, String[] maskParts) {
        StringBuilder networkAddress = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int ipSegment = Integer.parseInt(ipParts[i]);
            int maskSegment = Integer.parseInt(maskParts[i]);
            int networkSegment = ipSegment & maskSegment;
            networkAddress.append(networkSegment).append(i < 3 ? "." : "");
        }
        return networkAddress.toString();
    }

    // Function to calculate the broadcast address
    private static String calculateBroadcastAddress(String[] networkParts, String[] maskParts) {
        StringBuilder broadcastAddress = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int networkSegment = Integer.parseInt(networkParts[i]);
            int maskSegment = Integer.parseInt(maskParts[i]);
            int broadcastSegment = networkSegment | (~maskSegment & 255);
            broadcastAddress.append(broadcastSegment).append(i < 3 ? "." : "");
        }
        return broadcastAddress.toString();
    }
}
