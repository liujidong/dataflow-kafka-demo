package dataflow;

import com.google.cloud.dataflow.sdk.io.Sink.WriteOperation;
import com.google.cloud.dataflow.sdk.io.Sink.Writer;
import com.google.cloud.dataflow.sdk.values.KV;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class FileWrite extends Writer<KV<String, Long>, Boolean> {

	private BufferedWriter writer;
	private WriteOperation<KV<String, Long>, Boolean> writeOperation;

//	Connection con = null;
//	Statement st = null;
	
	public FileWrite(WriteOperation<KV<String, Long>, Boolean> writeOperation) {
		super();
		this.writeOperation = writeOperation;
	}
	@Override
	public void open(String uId) throws Exception {
		System.out.println("===================================================");
		System.out.println(uId);
		File uFile = new File(uId.toString());
		System.out.println(uFile.getAbsolutePath());
		writer = new BufferedWriter(new FileWriter(uFile));

//		con = JDBCUtil.getConnection();
//		st = con.createStatement();
	}

	@Override
	public void write(KV<String, Long> value) throws Exception {
		writer.write(value.getKey());
		writer.write("\t");
		writer.write(value.getValue().toString());
		writer.write("\n");

//		try {
//			String sql = " insert into test values('1','2');" ;
//			int i = st.executeUpdate(sql);
//			con.commit();
//			System.out.println("----insert data---"+i);;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				st.close();
//				con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
	}

	@Override
	public Boolean close() throws Exception {
		writer.close();
		return true;
	}

	@Override
	public WriteOperation<KV<String, Long>, Boolean> getWriteOperation() {
		return writeOperation;
	}

}
