import java.util.List;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

public class risingCity {

	public static void main(String[] args) throws Exception {
		boolean check = false; //check variable to see if the  building is already worked on 
		MinHeapImpl mHeap = new MinHeapImpl(2000);
		RedBTree redbt = new RedBTree();
		//String inputFileFromArg = "src/input.txt";
		String line;
		//BufferedReader reader = new BufferedReader(new FileReader(inputFileFromArg));
		BufferedReader reader = new BufferedReader(new FileReader("./"+args[0]));
		File writer = new File("./output_file.txt");
        BufferedWriter wr = new BufferedWriter(new FileWriter(writer));
		List<String> tmList = new ArrayList<>();
		List<String> operationToPerform = new ArrayList<>();

		while ((line = reader.readLine()) != null) {
			tmList.add(line.split(":")[0]);
			operationToPerform.add(line.split(":")[1]);
		}
		reader.close();

		int daysOfEffort = 0;
		while (true) {

			if (check && mHeap.getSize() == 0 && idx >= tmList.size()) {
				break;
			}

			if (!check) {
				OperationToPerform(mHeap, redbt, tmList, operationToPerform,wr);
				check = true;
				currentBldg = mHeap.getMin();
				daysOfEffort = 0;
			} else {
				OperationToPerform(mHeap, redbt, tmList, operationToPerform, wr);
				if(mHeap.getSize()==1) {
					currentBldg = mHeap.getMin();
				}
			}
			tm++;

			if (daysOfEffort == 0) {
				mHeap.minHeaps();
				currentBldg = mHeap.getMin();
			}
			// Work on the building
			if (currentBldg != null) {
				daysOfEffort++;
					currentBldg.setExecTime(currentBldg.getExecTime() + 1);
					if (currentBldg.getExecTime() == currentBldg.getTtlTime()) {
						OperationToPerform(mHeap, redbt, tmList, operationToPerform, wr);
						if(mHeap.getSize()==1) {
							currentBldg = mHeap.getMin();
						}
						print(currentBldg, tm, wr);
						mHeap.remove();
						redbt.remove(currentBldg);
						mHeap.minHeaps();
						currentBldg = mHeap.getMin();
						daysOfEffort = 0;
					}
				}

			if (daysOfEffort == 5) {
				mHeap.minHeaps();
				currentBldg = mHeap.getMin();
				daysOfEffort = 0;
			}
		}
		reader.close();
		wr.close();
	}

	/*Performs operations based on the input file and where ever necessary, writes the output to writer file*/
	private static void OperationToPerform(MinHeapImpl mHeap, RedBTree redbt, List<String> tmList,
			List<String> operationToPerform, BufferedWriter wr)  throws IOException {
		if ((idx < tmList.size()) && tm == Integer.parseInt(tmList.get(idx))) {
			// doOperation
			if (operationToPerform.get(idx).contains("Insert")) {
				String buildingNo = operationToPerform.get(idx).substring(operationToPerform.get(idx).indexOf("(") + 1,
						operationToPerform.get(idx).indexOf(","));
				String totalTime = operationToPerform.get(idx).substring(operationToPerform.get(idx).indexOf(",") + 1,
						operationToPerform.get(idx).indexOf(")"));
				if (!insert(mHeap, redbt, new Bldg(Integer.parseInt(buildingNo), 0, Integer.parseInt(totalTime)))) {
					return;
				}
			} else if (operationToPerform.get(idx).contains("Print")) {
				String range = operationToPerform.get(idx).substring(operationToPerform.get(idx).indexOf("(") + 1,
						operationToPerform.get(idx).indexOf(")"));
				if (range.contains(",")) {
					ArrayList<Bldg> list = RedBTree.searchRange(new ArrayList<>(), RedBTree.root,
							Integer.parseInt(range.split(",")[0]), Integer.parseInt(range.split(",")[1]));
					if(list.isEmpty()) {
						//System.out.println();
						wr.write("(0,0,0)");
						wr.newLine();
					}else {
						StringBuilder result = new StringBuilder();
						for (Bldg bldg : list) {
							result.append("(");
							result.append(bldg.getBldgID());
							result.append(",");
							result.append(bldg.getExecTime());
							result.append(",");
							result.append(bldg.getTtlTime());
							result.append(")");
							result.append(",");
						}
						//System.out.println(result.substring(0, result.length() - 1));
						wr.write(result.substring(0, result.length() - 1));
						wr.newLine();
					}
				} else {
					Bldg bldg = RedBTree.search(RedBTree.root, Integer.parseInt(range));
					if (bldg == null) {
						//System.out.println("(0,0,0)");
						wr.write("(0,0,0)");
						wr.newLine();
					} else {
						//System.out.println("(" + bldg.getBldgID() + "," + bldg.getExecTime() + "," + bldg.getTtlTime()+")");
						wr.write("(" + bldg.getBldgID() + "," + bldg.getExecTime() + "," + bldg.getTtlTime()+")");
						wr.newLine();
					}
				}
			}
			idx++;
		}

	}


	//data is inserted into minHeap and RedBTree by this method 
	public static boolean insert(MinHeapImpl mHeap, RedBTree redbt, Bldg bldg) {
		if (!redbt.add(bldg)) {
			return false;
		}
		mHeap.insert(bldg);
		return true;
	}
	
	/*This method prints the BuildingNum and time and returns an exception if the output stream is closed.*/
	private static void print(Bldg bldg, long tm, BufferedWriter wr) throws IOException {
		//System.out.println("(" + bldg.getBldgID() + "," + tm + ")");
		wr.write("(" + bldg.getBldgID() + "," + tm + ")");
		wr.newLine();
	}
	
	private static int idx = 0; //index for parsing through the input
	private static long tm = 0;	//to maintain global time. each increment of tm corresponds to one day. 
	private static Bldg currentBldg; //building selected to work on


}

