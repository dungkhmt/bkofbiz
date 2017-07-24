<div class="nav">
	<button class="icon-side-bar" onClick="openSideBar()">
		<span class="glyphicon glyphicon-menu-hamburger"></span>
	</button>

	<div class="app-name">
		<h1>BKEUNIV</h1>
	</div>
	
	<div class="action">
		<div class="tab-group">
		</div>
		<div class="user">
			<div class="user-name" onClick="openDropdownUser()" onBlur="closeDropdownUser()" tabIndex="0">
				DucDungDan
				<div class="dropdown-information glyphicon glyphicon-chevron-down">         
				</div>
				<div class="dropdown-content hidden">
					<a class="dropdown-item" href="/bkeuniv/control/user">Thông tin cá nhân</a>
					<a class="dropdown-item" href="/bkeuniv/control/logout">Đăng xuất</a>
				</div>
			</div>
		</div>
	</div>
</div>
