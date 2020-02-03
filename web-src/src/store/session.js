const SS = window.sessionStorage;
const SStorage={
	getItem(key) {
		try {
			return JSON.parse(SS.getItem(key));
		} catch (err) {
			return null;
		}
	},
	setItem(key, val) {
		SS.setItem(key, JSON.stringify(val));
	},
	clear() {
		SS.clear();
	},
	keys(index) {
		return SS.key(index);
	},
	removeItem(key) {
		SS.removeItem(key);
	}
}
export default SStorage;
