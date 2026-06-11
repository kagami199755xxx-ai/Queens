import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//13Queenまで、前回のコードより計算時間が50％削減
//11Queenは誤差しか出ませんので、12クイーンを使うことをお勧めします。
//プリントアウトはできますが、大量のログが出ますのでお勧めしません。
public class QueensBitmask_13 {

	static int N;
	static long limit;
	static long count = 0;

	public static void main(String[] args) {
		Long starttime = System.currentTimeMillis();
		N = 11; // 変更OK
		limit = (1L << N) - 1;
		List<List<Integer>> queens = new ArrayList<>();
		solve(queens, 0L, 0L, 0L, new int[N], 0);
		System.out.println("解の数 = " + queens.size());
		for (List<Integer> i : queens) {
			for (int j = 0; j < i.size(); j++) {
				System.out.print(i.get(j) + ", ");
			}
			System.out.println();
		}
		Long endtime = System.currentTimeMillis();
		System.out.println("処理時間:" + (endtime - starttime) + "ms");
		System.out.println("解の数 = " + queens.size());
	}

	// col, diag1, diag2 = 制約状態
	// row = 今の行
	// board = 解の記録（列番号）
	static void solve(List<List<Integer>> q, long col, long diag1, long diag2, int[] board, int row) {
		if (row == 1 && board[0] > N / 2) {
			return;
		}
		// 完了（N個置けた）
		if (row == N) {
			count++;

			// 解を出力
			q.add(Arrays.stream(board).boxed().collect(Collectors.toList()));
			dstct(q, N);
			return;
		}

		// 置ける場所
		long available = ~(col | diag1 | diag2) & limit;

		while (available != 0) {

			long pos = available & -available; // 1つだけ取り出す
			available -= pos;

			int colIndex = Long.numberOfTrailingZeros(pos);

			board[row] = colIndex;

			solve(q,
					col | pos,
					(diag1 | pos) << 1,
					(diag2 | pos) >> 1,
					board,
					row + 1);
		}
	}

	static void dstct(List<List<Integer>> q, int N) {
		List<Integer> current_list = q.getLast();
		List<Integer> dl2 = new ArrayList<>();
		List<Integer> dl3 = new ArrayList<>();
		List<Integer> dl4 = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			dl2.add(N - current_list.get(i) - 1);
			dl3.add(current_list.indexOf(i));
			dl4.add(N - current_list.indexOf(i) - 1);
		}
		List<Integer> dl1 = current_list.reversed();
		List<Integer> dl5 = dl2.reversed();
		List<Integer> dl6 = dl3.reversed();
		List<Integer> dl7 = dl4.reversed();

		if (!current_list.equals(dl1)) {
			q.remove(dl1);
		}
		if (!current_list.equals(dl2)) {
			q.remove(dl2);
		}
		if (!current_list.equals(dl3)) {
			q.remove(dl3);
		}
		if (!current_list.equals(dl4)) {
			q.remove(dl4);
		}
		if (!current_list.equals(dl5)) {
			q.remove(dl5);
		}
		if (!current_list.equals(dl6)) {
			q.remove(dl6);
		}
		if (!current_list.equals(dl7)) {
			q.remove(dl7);
		}
	}
}