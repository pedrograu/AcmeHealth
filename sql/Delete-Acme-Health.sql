start transaction;

use `Acme-Health`;

revoke all privileges on `Acme-Health`.* from `acme-user`@`%`;
revoke all privileges on `Acme-Health`.* from `acme-manager`@`%`;

drop user `acme-user`@`%`;
drop user `acme-manager`@`%`;

drop database `Acme-Health`;

commit;